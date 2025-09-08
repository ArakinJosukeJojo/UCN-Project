using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;
using System.Threading.Tasks;
using System.Transactions;
using DataAccess.ModelLayer;
using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Configuration;

namespace DataAccess.DatabaseLayer
{

    public class ReservationAccess : IReservationAccess
    {
        private readonly string _connectionString;
        private readonly IRoomtypeAccess? _roomtypeAccess;
        private readonly IRoomAccess? _roomAccess;
        private System.Data.IsolationLevel theIsolationLevel = System.Data.IsolationLevel.ReadUncommitted;

        public ReservationAccess(string _connectionString_)
        {
            _connectionString = _connectionString_;
            _roomtypeAccess = new RoomtypeAccess(_connectionString);
            _roomAccess = new RoomAccess(_connectionString);
        }

        public ReservationAccess(IConfiguration inConfig)
        {
            _connectionString = inConfig.GetConnectionString("DatabaseConnection");
            _roomtypeAccess = new RoomtypeAccess(inConfig);
            _roomAccess = new RoomAccess(inConfig);
        }

        public void CreateReservation()
        {
            throw new NotImplementedException();
        }

        public bool Transaction(Reservation reservation)
        {
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                con.Open();
                SqlTransaction transaction = con.BeginTransaction(theIsolationLevel);
                try
                {
                    if (!CheckBefore(con, transaction, reservation.ReservationStartDate, reservation.ReservationEndDate, reservation.ReservationLines))
                        throw new Exception("Pre-check failed: Rooms not available.");

                    int addressId = InsertIntoAddress(con, transaction, reservation.person.Address);
                    InsertIntoPerson(con, transaction, reservation.person, addressId);
                    int reservationNo = InsertIntoReservation(con, transaction, reservation);
                    InsertIntoReservationLine(con, transaction, reservation.ReservationLines, reservationNo);

                    if (!CheckAfter(con, transaction, reservation.ReservationStartDate, reservation.ReservationEndDate, reservation.ReservationLines))
                        throw new Exception("Post-check failed: Room state changed.");

                    transaction.Commit();
                    SendConfirmation(reservationNo, con);
                    return true;
                }
                catch (Exception ex)
                {
                        transaction.Rollback();
                        return false;
                }
            }
        }

        //Returns the addressId of the inserted address, to use in InsertIntoPerson
        private int InsertIntoAddress(SqlConnection con, SqlTransaction transaction, Address address)
        {
            int addressId;
            string addressString = "INSERT INTO PersonAddress (StreetName, HouseNo, FloorNo, ZipCode, Country) OUTPUT INSERTED.AddressId VALUES(@StreetName, @HouseNo, @FloorNo, @ZipCode, @Country)";
            using (SqlCommand cmd = new SqlCommand(addressString, con))
            {
                cmd.Transaction = transaction;
                cmd.Parameters.AddRange(new[]
                {
                        new SqlParameter("@StreetName", address.StreetName),
                        new SqlParameter("@HouseNo", address.HouseNumber),
                        new SqlParameter("@FloorNo", address.Floor),
                        new SqlParameter("@ZipCode", address.ZipCode),
                        new SqlParameter("@Country", address.Country)
                });
                addressId = (int)cmd.ExecuteScalar();
                return addressId;
            }
        }

        private void InsertIntoPerson(SqlConnection con, SqlTransaction transaction, Person person, int addressId)
        {
            string personString = "INSERT INTO Persons (PhoneNo, FName, LName, Email, AddressId, PersonTypeId) VALUES(@PhoneNo, @FName, @LName, @Email, @AddressId, @PersonTypeId)";
            using (SqlCommand cmd = new SqlCommand(personString, con))
            {
                cmd.Transaction = transaction;
                cmd.Parameters.AddRange(new[] {
                        new SqlParameter("@PhoneNo", person.PhoneNumber),
                        new SqlParameter("@FName", person.FirstName),
                        new SqlParameter("@LName", person.LastName),
                        new SqlParameter("@Email", person.Email),
                        new SqlParameter("@AddressId", addressId),
                        new SqlParameter("@PersonTypeId", 1)
                });
                cmd.ExecuteNonQuery();
            }
        }

        //Returns reservationNo of the inserted reservation, to use in InsertIntoReservationLine
        private int InsertIntoReservation(SqlConnection con, SqlTransaction transaction, Reservation reservation)
        {
            int reservationNo;
            string reservationString = "INSERT INTO Reservations(ReservationStartDate, ReservationEndDate, CheckedIn, CheckedOut, PayAtLocation, IsConfirmationSent, IsPaid, ReservationNote, PhoneNo) OUTPUT INSERTED.ReservationNo VALUES(@ReservationStartDate, @ReservationEndDate, 0, 0, @PayAtLocation, 0, @IsPaid, @ReservationNote, @PhoneNo)";
            using (SqlCommand cmd = new SqlCommand(reservationString, con))
            {
                cmd.Transaction = transaction;
                cmd.Parameters.AddRange(new[] {
                        new SqlParameter("@ReservationStartDate", reservation.ReservationStartDate),
                        new SqlParameter("@ReservationEndDate", reservation.ReservationEndDate),
                        new SqlParameter("@PayAtLocation", reservation.PayAtLocation),
                        new SqlParameter("@IsPaid", reservation.IsPaid),
                        new SqlParameter("@ReservationNote", reservation.ReservationNote),
                        new SqlParameter("@PhoneNo", reservation.person.PhoneNumber)
                });
                reservationNo = (int)cmd.ExecuteScalar();
                return reservationNo;
            }
        }

        private void InsertIntoReservationLine(SqlConnection con, SqlTransaction transaction, List<ReservationLine> reservationLines, int reservationNo)
        {
            string insertString = "INSERT INTO ReservationsLines(ReservationNo, RoomTypeId, Amount) VALUES (@ReservationNo, @RoomTypeId, @Amount)";

            using (SqlCommand cmd = new SqlCommand(insertString, con))
            {
                cmd.Transaction = transaction;

                foreach (ReservationLine r2 in reservationLines)
                {
                    // Hver iteration får nye parametre
                    cmd.Parameters.Clear();

                    //Ikke brugt addRange her, da det er bedre i loops
                    cmd.Parameters.Add(new SqlParameter("@ReservationNo", reservationNo));
                    cmd.Parameters.Add(new SqlParameter("@RoomTypeId", r2.RoomType.RoomTypeId));
                    cmd.Parameters.Add(new SqlParameter("@Amount", r2.Amount));

                    cmd.ExecuteNonQuery();
                }
            }
        }

        // Checks if there are enough rooms available before the reservation is made
        private bool CheckBefore(SqlConnection con, SqlTransaction transaction, DateTime startDate, DateTime endDate, List<ReservationLine> reservationLines)
        {
            foreach (ReservationLine r in reservationLines)
            {
                List<DateTime> dates = new List<DateTime>();
                dates.Add(startDate);
                dates.Add(endDate);
                int rooms = GetAvailableRoomCheck(con, transaction, r.RoomType.RoomTypeId);
                int bookedRooms = GetRoomTypeavailabilityCheck(con, transaction, r.RoomType, dates);
                if (rooms - (bookedRooms + r.Amount) < 0)
                {
                    return false;
                }

            }
            return true;
        }

        // Checks if there are enough rooms available after the reservation is made
        private bool CheckAfter(SqlConnection con, SqlTransaction transaction, DateTime startDate, DateTime endDate, List<ReservationLine> reservationLines)
        {
            foreach (ReservationLine r in reservationLines)
            {                
                List<DateTime> dates = new List<DateTime>();
                dates.Add(startDate);
                dates.Add(endDate);
                int rooms = GetAvailableRoomCheck(con, transaction, r.RoomType.RoomTypeId);
                int bookedRooms = GetRoomTypeavailabilityCheck(con, transaction, r.RoomType, dates);
                if (rooms - bookedRooms < 0)
                {
                    return false;
                }

            }
            return true;
        }



        public int GetRoomTypeavailabilityCheck(SqlConnection con, SqlTransaction transaction, RoomType roomType, List<DateTime> dates)
        {
            DateTime startdate = dates[0];
            DateTime enddate = dates[1];
            int foundRoomavailability = 0;

            string queryString = @"
                SELECT SUM(amount) AS bookedRooms
                FROM ReservationsLines 
                LEFT JOIN Reservations ON Reservations.ReservationNo = ReservationsLines.ReservationNo 
                WHERE roomTypeId = @roomTypeid 
                AND (
                    (Reservations.ReservationStartDate < @StartDate AND Reservations.ReservationEndDate > @StartDate) 
                    OR (Reservations.ReservationEndDate > @EndDate AND Reservations.ReservationStartDate < @EndDate) 
                    OR (Reservations.ReservationStartDate >= @StartDate AND Reservations.ReservationEndDate <= @EndDate)
                )";

            using (SqlCommand readCommand = new SqlCommand(queryString, con, transaction))
            {
                readCommand.Parameters.AddWithValue("@roomTypeid", roomType.RoomTypeId);
                readCommand.Parameters.AddWithValue("@StartDate", startdate);
                readCommand.Parameters.AddWithValue("@EndDate", enddate);

                using (SqlDataReader reader = readCommand.ExecuteReader())
                {
                    if (reader.Read() && !reader.IsDBNull(reader.GetOrdinal("bookedRooms")))
                    {
                        foundRoomavailability = reader.GetInt32(reader.GetOrdinal("bookedRooms"));
                    }
                }
            }
            return foundRoomavailability;
        }

        public int GetAvailableRoomCheck(SqlConnection con, SqlTransaction transaction, int roomId)
        {
            int foundAvailability = 0;
            string queryString = "SELECT COUNT(*) FROM Rooms WHERE roomTypeId = @workingRoomTypeID";
            using (SqlCommand readCommand = new SqlCommand(queryString, con, transaction))
            {
                readCommand.Parameters.AddWithValue("@workingRoomTypeID", roomId);

                using (SqlDataReader personReader = readCommand.ExecuteReader())
                {
                    if (personReader.Read())
                    {
                        foundAvailability = personReader.GetInt32(0);
                    }
                }
            }
            return foundAvailability;
        }

        public bool SendConfirmation(int reservationNo, SqlConnection con)
        {

            try
            {
                string updateConfirmationString = "UPDATE Reservations SET IsConfirmationSent = 1 WHERE ReservationNo = @ReservationNo";
                using (SqlCommand readCommand = new SqlCommand(updateConfirmationString, con))
                {
                    readCommand.Parameters.AddWithValue("@ReservationNo", reservationNo);
                    readCommand.ExecuteNonQuery();
                }
            }
            catch (SqlException ex)
            {
                // Handle SQL exception
                Console.WriteLine("SQL Error: " + ex.Message);
                return false;
            }
            catch (Exception ex)
            {
                // Handle general exception
                Console.WriteLine("Error: " + ex.Message);
                return false;
            }
            finally
            {
                // Ensure the connection is closed
                if (con.State == System.Data.ConnectionState.Open)
                {
                    con.Close();
                }
            }
            return true;
        }
    }
}
