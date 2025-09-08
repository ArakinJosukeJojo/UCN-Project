using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataAccess.ModelLayer;
using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Configuration;

namespace DataAccess.DatabaseLayer
{
    public class BookingAccess : IBookingAccess
    {
        private readonly string _connectionString;
        private readonly IRoomtypeAccess? _roomtypeAccess;
        private readonly IRoomAccess? _roomAccess;
        private System.Data.IsolationLevel theIsolationLevel = System.Data.IsolationLevel.ReadUncommitted;

        public BookingAccess(string _connectionString_)
        {
            _connectionString = _connectionString_;
            _roomtypeAccess = new RoomtypeAccess(_connectionString);
            _roomAccess = new RoomAccess(_connectionString);
        }

        public BookingAccess(IConfiguration inConfig)
        {
            _connectionString = inConfig.GetConnectionString("DatabaseConnection");
            _roomtypeAccess = new RoomtypeAccess(inConfig);
            _roomAccess = new RoomAccess(inConfig);
        }

        //Ændrer returtype til List<int> for at returnere alle tildelte værelsesnumre
        public List<string>? CheckIn(int reservationNO)
        {
            List<string> assignedRooms = new List<string>();
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                con.Open();
                SqlTransaction transaction = con.BeginTransaction(theIsolationLevel);

                try
                {
                    // Step 1: Check if reservation exists and not already checked in
                    string checkQuery = @"
                SELECT CheckedIn 
                FROM Reservations 
                WHERE ReservationNo = @ReservationNo";

                    using (SqlCommand checkCmd = new SqlCommand(checkQuery, con, transaction))
                    {
                        checkCmd.Parameters.AddWithValue("@ReservationNo", reservationNO);
                        var result = checkCmd.ExecuteScalar();

                        if (result == null || Convert.ToBoolean(result))
                        {
                            transaction.Rollback();
                            return null;
                        }
                    }

                    // Step 2: Get all room types and their quantities
                    string getLinesQuery = @"
                SELECT RoomTypeId, Amount 
                FROM ReservationsLines 
                WHERE ReservationNo = @ReservationNo";

                    Dictionary<int, int> roomTypeToAmount = new Dictionary<int, int>();

                    using (SqlCommand getLinesCmd = new SqlCommand(getLinesQuery, con, transaction))
                    {
                        getLinesCmd.Parameters.AddWithValue("@ReservationNo", reservationNO);
                        using (SqlDataReader reader = getLinesCmd.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                int roomTypeId = reader.GetInt32(0);
                                int amount = reader.GetInt32(1);
                                roomTypeToAmount[roomTypeId] = amount;
                            }
                        }
                    }

                    // Step 3: Try to assign available rooms for each room type
                    foreach (var entry in roomTypeToAmount)
                    {
                        int roomTypeId = entry.Key;
                        int amountNeeded = entry.Value;

                        string findRoomsQuery = @"
                    SELECT TOP (@Amount) RoomNo 
                    FROM Rooms 
                    WHERE IsOccupied = 0 AND RoomTypeId = @RoomTypeId";

                        List<string> availableRoomNos = new List<string>();

                        using (SqlCommand findRoomsCmd = new SqlCommand(findRoomsQuery, con, transaction))
                        {
                            findRoomsCmd.Parameters.AddWithValue("@Amount", amountNeeded);
                            findRoomsCmd.Parameters.AddWithValue("@RoomTypeId", roomTypeId);

                            using (SqlDataReader reader = findRoomsCmd.ExecuteReader())
                            {
                                while (reader.Read())
                                {
                                    availableRoomNos.Add(reader.GetString(0));
                                }
                            }
                        }

                        if (availableRoomNos.Count < amountNeeded)
                        {
                            transaction.Rollback();
                            return null; // Not enough rooms available for this type
                        }

                        // Assign rooms
                        foreach (string roomNo in availableRoomNos)
                        {
                            string updateRoomQuery = @"
                        UPDATE Rooms
                        SET IsOccupied = 1,
                            ReservationNo = @ReservationNo
                        WHERE RoomNo = @RoomNo";

                            using (SqlCommand updateRoomCmd = new SqlCommand(updateRoomQuery, con, transaction))
                            {
                                updateRoomCmd.Parameters.AddWithValue("@ReservationNo", reservationNO);
                                updateRoomCmd.Parameters.AddWithValue("@RoomNo", roomNo);
                                updateRoomCmd.ExecuteNonQuery();
                            }
                            assignedRooms.Add(roomNo);
                        }
                    }

                    // Step 4: Update reservation to CheckedIn
                    string updateResQuery = @"
                UPDATE Reservations
                SET CheckedIn = 1, CheckedOut = 0
                WHERE ReservationNo = @ReservationNo";

                    using (SqlCommand updateResCmd = new SqlCommand(updateResQuery, con, transaction))
                    {
                        updateResCmd.Parameters.AddWithValue("@ReservationNo", reservationNO);
                        updateResCmd.ExecuteNonQuery();
                    }

                    transaction.Commit();
                    return assignedRooms;
                }
                catch (Exception)
                {
                    transaction.Rollback();
                    return null;
                }
            }
        }



        public List<string>? CheckOut(int resavationNO)
        {
            List<string> checkedOutRooms = new List<string>();
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                con.Open();
                SqlTransaction transaction = con.BeginTransaction(theIsolationLevel);

                try
                {
                    // Step 1: Check if reservation exists and is currently checked in
                    string checkQuery = @"
                SELECT CheckedIn, CheckedOut 
                FROM Reservations 
                WHERE ReservationNo = @ReservationNo";

                    bool isCheckedIn = false;
                    bool isCheckedOut = false;

                    using (SqlCommand checkCmd = new SqlCommand(checkQuery, con, transaction))
                    {
                        checkCmd.Parameters.AddWithValue("@ReservationNo", resavationNO);
                        using (SqlDataReader reader = checkCmd.ExecuteReader())
                        {
                            if (!reader.Read())
                            {
                                transaction.Rollback();
                                return new List<string> { "Reservation not found." };
                            }

                            isCheckedIn = reader.GetBoolean(0);
                            isCheckedOut = reader.GetBoolean(1);
                        }
                    }

                    if (!isCheckedIn || isCheckedOut)
                    {
                        transaction.Rollback();
                        return new List<string> { "Reservation is either not checked in or already checked out." };
                    }

                    // Step 2: Get room numbers before freeing them
                    string getRoomsQuery = @"
                SELECT RoomNo FROM Rooms
                WHERE ReservationNo = @ReservationNo";

                    using (SqlCommand getRoomsCmd = new SqlCommand(getRoomsQuery, con, transaction))
                    {
                        getRoomsCmd.Parameters.AddWithValue("@ReservationNo", resavationNO);
                        using (SqlDataReader reader = getRoomsCmd.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                checkedOutRooms.Add(reader["RoomNo"].ToString() ?? "Unknown Room");
                            }
                        }
                    }

                    // Step 3: Free the rooms
                    string freeRoomsQuery = @"
                UPDATE Rooms
                SET IsOccupied = 0,
                    ReservationNo = NULL
                WHERE ReservationNo = @ReservationNo";

                    using (SqlCommand freeRoomsCmd = new SqlCommand(freeRoomsQuery, con, transaction))
                    {
                        freeRoomsCmd.Parameters.AddWithValue("@ReservationNo", resavationNO);
                        freeRoomsCmd.ExecuteNonQuery();
                    }

                    // Step 4: Update reservation to CheckedOut
                    string updateResQuery = @"
                UPDATE Reservations
                SET CheckedIn = 0, CheckedOut = 1
                WHERE ReservationNo = @ReservationNo";

                    using (SqlCommand updateResCmd = new SqlCommand(updateResQuery, con, transaction))
                    {
                        updateResCmd.Parameters.AddWithValue("@ReservationNo", resavationNO);
                        updateResCmd.ExecuteNonQuery();
                    }

                    transaction.Commit();
                    checkedOutRooms.Insert(0, "Guest has been checked out. Rooms are now free:");
                    return checkedOutRooms;
                }
                catch (Exception ex)
                {
                    transaction.Rollback();
                    return new List<string> { "An error occurred during checkout." };
                }
            }
        }



        public bool AflysReservation(int reservationsNo)
        {
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                con.Open();
                SqlTransaction transaction = con.BeginTransaction(IsolationLevel.Serializable);

                try
                {
                    // Step 1: Check if reservation exists and is NOT checked in or checked out
                    string checkQuery = @"
                SELECT CheckedIn, CheckedOut 
                FROM Reservations 
                WHERE ReservationNo = @ReservationNo";

                    bool isCheckedIn, isCheckedOut;

                    using (SqlCommand checkCmd = new SqlCommand(checkQuery, con, transaction))
                    {
                        checkCmd.Parameters.AddWithValue("@ReservationNo", reservationsNo);
                        using (SqlDataReader reader = checkCmd.ExecuteReader())
                        {
                            if (!reader.Read())
                            {
                                transaction.Rollback();
                                return false; // No such reservation
                            }

                            isCheckedIn = reader.GetBoolean(0);
                            isCheckedOut = reader.GetBoolean(1);
                        }
                    }

                    // Cannot cancel if already checked in or out
                    if (isCheckedIn || isCheckedOut)
                    {
                        transaction.Rollback();
                        return false;
                    }
                    // Does it make sense to check?
                    // Step 2: Free up rooms associated with this reservation
                    string freeRoomsQuery = @"
                UPDATE Rooms
                SET IsOccupied = 0,
                    ReservationNo = NULL
                WHERE ReservationNo = @ReservationNo";

                    using (SqlCommand cmd = new SqlCommand(freeRoomsQuery, con, transaction))
                    {
                        cmd.Parameters.AddWithValue("@ReservationNo", reservationsNo);
                        cmd.ExecuteNonQuery();
                    }

                    // Step 3: Delete from Quantities table
                    string deleteQuantitiesQuery = @"
                DELETE FROM Quantities 
                WHERE ReservationNo = @ReservationNo";

                    using (SqlCommand cmd = new SqlCommand(deleteQuantitiesQuery, con, transaction))
                    {
                        cmd.Parameters.AddWithValue("@ReservationNo", reservationsNo);
                        cmd.ExecuteNonQuery();
                    }

                    // Step 4: Delete from ReservationsLines
                    string deleteLinesQuery = @"
                DELETE FROM ReservationsLines 
                WHERE ReservationNo = @ReservationNo";

                    using (SqlCommand cmd = new SqlCommand(deleteLinesQuery, con, transaction))
                    {
                        cmd.Parameters.AddWithValue("@ReservationNo", reservationsNo);
                        cmd.ExecuteNonQuery();
                    }

                    // Step 5: Finally, delete from Reservations
                    string deleteReservationQuery = @"
                DELETE FROM Reservations 
                WHERE ReservationNo = @ReservationNo";

                    using (SqlCommand cmd = new SqlCommand(deleteReservationQuery, con, transaction))
                    {
                        cmd.Parameters.AddWithValue("@ReservationNo", reservationsNo);
                        cmd.ExecuteNonQuery();
                    }

                    transaction.Commit();
                    return true;
                }
                catch (Exception)
                {
                    transaction.Rollback();
                    return false;
                }
            }
        }
    }
}