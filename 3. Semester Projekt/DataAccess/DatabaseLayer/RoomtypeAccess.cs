using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Configuration;
using DataAccess.ModelLayer;
using System.Diagnostics;
using System.Reflection.PortableExecutable;
using System.Diagnostics.Contracts;
using System.Diagnostics.Metrics;
using System.Reflection.Emit;

namespace DataAccess.DatabaseLayer
{
    public class RoomtypeAccess : IRoomtypeAccess
    {
        private readonly string? _connectionString;

        public RoomtypeAccess(IConfiguration inConfig)
        {
            _connectionString = inConfig.GetConnectionString("DatabaseConnection");
        }
        public RoomtypeAccess(string _connectionString_)
        {
            _connectionString = _connectionString_;
        }


        public List<RoomType> GetAllRoomTypes()
        {

            List<RoomType> foundRoomTypes;
            RoomType readRoomType;
            //TooDo SQL Script
            string queryString = "SELECT RoomTypes.RoomTypeId, RoomTypes.RoomTypeName, RoomTypes.RoomTypeDescription, RoomTypes.bedSpace, Prices.price FROM RoomTypes LEFT JOIN Prices ON RoomTypes.RoomTypeId = Prices.roomTypeId";

            using (SqlConnection con = new SqlConnection(_connectionString))
            using (SqlCommand readCommand = new SqlCommand(queryString, con))
            {

                con.Open();
                SqlDataReader RoomTypeReader = readCommand.ExecuteReader();
                foundRoomTypes = new List<RoomType>();
                while (RoomTypeReader.Read())
                {
                    readRoomType = GetRoomTypeFromReader(RoomTypeReader);
                    foundRoomTypes.Add(readRoomType);
                }

            }
            return foundRoomTypes;
        }

        public List<RoomType> GetRoomTypeByDate()
        {
            //TODO
            //Implement this method to get room types by date
            throw new NotImplementedException();
        }


        public List<RoomType> GetRoomTypesByDate(List<DateTime> searchDate)
        {

            if (searchDate == null || searchDate.Count < 2)
                throw new ArgumentException("searchDate must contain both start and end dates.");

            List<RoomType> foundRoomTypes = new List<RoomType>();
            RoomType readRoomType;

            // Updated SQL query to filter by date range
            string queryString = @"
                SELECT DISTINCT 
                    rt.RoomTypeId, 
                    rt.RoomTypeName, 
                    rt.RoomTypeDescription, 
                    rt.BedSpace, 
                    p.Price, 
                    r.ReservationNo, 
                    r.ReservationStartDate, 
                    r.ReservationEndDate,
                    r.CheckedIn,
                    r.CheckedOut,
                    per.FName,
                    per.LName,
                    per.PhoneNo,
                    pa.StreetName,
                    pa.HouseNo,
                    pa.FloorNo,
                    pa.ZipCode,
                    pa.Country
                FROM RoomTypes rt
                LEFT JOIN Prices p ON p.RoomTypeId = rt.RoomTypeId
                INNER JOIN ReservationsLines rl ON rl.RoomTypeId = rt.RoomTypeId
                INNER JOIN Reservations r ON r.ReservationNo = rl.ReservationNo
                INNER JOIN Persons per ON r.PhoneNo = per.PhoneNo
                INNER JOIN PersonAddress pa ON per.AddressId = pa.AddressId
                  WHERE r.ReservationStartDate <= @EndDate
                    AND r.ReservationEndDate >= @StartDate";
            using (SqlConnection con = new SqlConnection(_connectionString))
            using (SqlCommand readCommand = new SqlCommand(queryString, con))
            {
                readCommand.Parameters.AddWithValue("@StartDate", searchDate[0]);
                readCommand.Parameters.AddWithValue("@EndDate", searchDate[1]);

                con.Open();
                using (SqlDataReader RoomTypeReader = readCommand.ExecuteReader())
                {
                    while (RoomTypeReader.Read())
                    {
                        readRoomType = GetRoomTypeFromReader2(RoomTypeReader);
                        foundRoomTypes.Add(readRoomType);
                    }
                }
            }

            return foundRoomTypes;
        }

        public List<RoomType> GetRoomTypesByName(List<string> searchName)
        {
            List<RoomType> foundRoomTypes = new List<RoomType>();
            RoomType readRoomType;

            string queryString = @"
            SELECT DISTINCT 
            rt.RoomTypeId, 
            rt.RoomTypeName, 
            rt.RoomTypeDescription, 
            rt.BedSpace, 
            p.Price, 
            r.ReservationNo, 
            r.ReservationStartDate, 
            r.ReservationEndDate,
            r.CheckedIn,
            r.CheckedOut,
            per.FName,
            per.LName,
            per.PhoneNo,
            pa.StreetName,
            pa.HouseNo,
            pa.FloorNo,
            pa.ZipCode,
            pa.Country
            FROM RoomTypes rt
            LEFT JOIN Prices p ON p.RoomTypeId = rt.RoomTypeId
            INNER JOIN ReservationsLines rl ON rl.RoomTypeId = rt.RoomTypeId
            INNER JOIN Reservations r ON r.ReservationNo = rl.ReservationNo
            INNER JOIN Persons per ON r.PhoneNo = per.PhoneNo
            INNER JOIN PersonAddress pa ON per.AddressId = pa.AddressId
            WHERE (@FirstName IS NULL OR per.FName LIKE '%' + @FirstName + '%')
              AND (@LastName IS NULL OR per.LName LIKE '%' + @LastName + '%')";


            using (SqlConnection con = new SqlConnection(_connectionString))
            using (SqlCommand readCommand = new SqlCommand(queryString, con))
            {
                string firstName = searchName.Count > 0 ? searchName[0] : null;
                string lastName = searchName.Count > 1 ? searchName[1] : null;

                readCommand.Parameters.AddWithValue("@FirstName", string.IsNullOrWhiteSpace(firstName) ? DBNull.Value : firstName);
                readCommand.Parameters.AddWithValue("@LastName", string.IsNullOrWhiteSpace(lastName) ? DBNull.Value : lastName);

                con.Open();
                using (SqlDataReader RoomTypeReader = readCommand.ExecuteReader())
                {
                    while (RoomTypeReader.Read())
                    {
                        readRoomType = GetRoomTypeFromReader2(RoomTypeReader);
                        foundRoomTypes.Add(readRoomType);
                    }
                }
            }

            return foundRoomTypes;
        }



        private RoomType GetRoomTypeFromReader2(SqlDataReader RoomTypeReader)
        {
            RoomType foundRoomType;
            int tempRoomtypeId;
            string tempName;
            string tempdescription;
            int tempBeedSpace;
            Decimal tempPrice;

            int tempReservationNo;
            DateTime tempReservationStartDate;
            DateTime tempReservationEndDate;
            bool tempCheckedIn;
            bool tempCheckedOut;
            string tempFirstName;
            string tempLastName;
            //int tempPhoneNo;
            string tempPhoneNo;

            string tempStreetName;
            int tempHouseNo;
            string tempFloorNo;
            int tempZipCode;
            string tempCountry;


            //tjek at strings er ok
            tempRoomtypeId = RoomTypeReader.GetInt32(RoomTypeReader.GetOrdinal("roomTypeId"));
            tempName = RoomTypeReader.GetString(RoomTypeReader.GetOrdinal("roomTypeName"));
            tempdescription = RoomTypeReader.GetString(RoomTypeReader.GetOrdinal("roomTypeDescription"));
            tempBeedSpace = RoomTypeReader.GetInt32(RoomTypeReader.GetOrdinal("BedSpace"));
            tempPrice = RoomTypeReader.GetDecimal(RoomTypeReader.GetOrdinal("price"));

            tempReservationNo = RoomTypeReader.GetInt32(RoomTypeReader.GetOrdinal("ReservationNo"));
            tempReservationStartDate = RoomTypeReader.GetDateTime(RoomTypeReader.GetOrdinal("ReservationStartDate"));
            tempReservationEndDate = RoomTypeReader.GetDateTime(RoomTypeReader.GetOrdinal("ReservationEndDate"));
            tempCheckedIn = RoomTypeReader.GetBoolean(RoomTypeReader.GetOrdinal("CheckedIn"));
            tempCheckedOut = RoomTypeReader.GetBoolean(RoomTypeReader.GetOrdinal("CheckedOut"));

            tempFirstName = RoomTypeReader.GetString(RoomTypeReader.GetOrdinal("FName"));
            tempLastName = RoomTypeReader.GetString(RoomTypeReader.GetOrdinal("LName"));
            tempPhoneNo = RoomTypeReader.GetString(RoomTypeReader.GetOrdinal("PhoneNo"));

            tempStreetName = RoomTypeReader.GetString(RoomTypeReader.GetOrdinal("StreetName"));
            tempHouseNo = RoomTypeReader.GetInt32(RoomTypeReader.GetOrdinal("HouseNo"));
            tempFloorNo = RoomTypeReader.GetString(RoomTypeReader.GetOrdinal("FloorNo"));
            tempZipCode = RoomTypeReader.GetInt32(RoomTypeReader.GetOrdinal("ZipCode"));
            tempCountry = RoomTypeReader.GetString(RoomTypeReader.GetOrdinal("Country"));


            // Construct Address object
            Address guestAddress = new Address(tempStreetName, tempHouseNo, tempFloorNo, tempZipCode, tempCountry);

            // Construct Person object (you can pass "" for Email if not used)
            Person guest = new Person(tempFirstName, tempLastName, "", tempPhoneNo, null);  // Passing null for Address if unused in Person

            // Construct RoomType
            foundRoomType = new RoomType(
                tempRoomtypeId,
                tempName,
                tempdescription,
                tempBeedSpace,
                tempPrice,
                tempReservationNo,
                tempReservationStartDate,
                tempReservationEndDate,
                guest,
                guestAddress,
                tempCheckedIn,
                tempCheckedOut);

            return foundRoomType;
        }


        //private RoomType GetRoomTypeFromReaderDate(SqlDataReader reader)
        //{
        //    int roomTypeId = reader.GetInt32(reader.GetOrdinal("RoomTypeId"));
        //    string name = reader.GetString(reader.GetOrdinal("RoomTypeName"));
        //    string desc = reader.GetString(reader.GetOrdinal("RoomTypeDescription"));
        //    int bedSpace = reader.GetInt32(reader.GetOrdinal("BedSpace"));
        //    decimal price = reader.GetDecimal(reader.GetOrdinal("Price"));

        //    int reservationNo = reader.GetInt32(reader.GetOrdinal("ReservationNo"));
        //    DateTime reservationStartDate = reader.GetDateTime(reader.GetOrdinal("ReservationStartDate"));
        //    DateTime reservationEndDate = reader.GetDateTime(reader.GetOrdinal("ReservationEndDate"));
        //    string guestFirstName = reader.GetString(reader.GetOrdinal("FName"));
        //    string guestLastName = reader.GetString(reader.GetOrdinal("LName"));
        //    int guestPhoneNo = reader.GetInt32(reader.GetOrdinal("PhoneNo"));


        //    RoomType roomType = new RoomType(roomTypeId, name, desc, bedSpace, price, reservationNo,
        //        reservationStartDate, reservationEndDate, guestFirstName, guestLastName, guestPhoneNo);

        //    return roomType;
        //}

        private RoomType GetRoomTypeFromReader(SqlDataReader RoomTypeReader)
        {
            RoomType foundRoomType;
            int tempRoomtypeId;
            string tempName;
            string tempdescription;
            int tempBeedSpace;
            Decimal tempPrice;
            //tjek at strings er ok
            tempRoomtypeId = RoomTypeReader.GetInt32(RoomTypeReader.GetOrdinal("roomTypeId"));
            tempName = RoomTypeReader.GetString(RoomTypeReader.GetOrdinal("roomTypeName"));
            tempdescription = RoomTypeReader.GetString(RoomTypeReader.GetOrdinal("roomTypeDescription"));
            tempBeedSpace = RoomTypeReader.GetInt32(RoomTypeReader.GetOrdinal("BedSpace"));
            tempPrice = RoomTypeReader.GetDecimal(RoomTypeReader.GetOrdinal("price"));
            foundRoomType = new RoomType(tempRoomtypeId, tempName, tempdescription, tempBeedSpace, tempPrice);
            return foundRoomType;
        }

        //public int GetRoomTypeavailability(RoomType roomType, List<DateTime> dates)
        //{
        //    DateTime startdate = dates[0];
        //    string startString = startdate.ToShortDateString();
        //    DateTime enddate = dates[1];
        //    string endString = enddate.ToShortDateString();
        //    int foundRoomavailability = 0;
        //    //Kan Laves om til noget mere smart
        //    string queryString = "SELECT amount FROM ReservationsLines LEFT JOIN Reservations ON Reservations.ReservationNo = ReservationsLines.ReservationNo Where roomTypeId = @roomTypeid AND ((Reservations.ReservationStartDate < @StartDate AND Reservations.reservationEndDate > @StartDate) OR (Reservations.reservationEndDate > @EndDate AND Reservations.reservationStartDate < @EndDate) OR (Reservations.reservationStartDate > @StartDate AND Reservations.reservationEndDate < @EndDate))";
        //    using (SqlConnection con = new SqlConnection(_connectionString))
        //    using (SqlCommand readCommand = new SqlCommand(queryString, con))
        //    {//test
        //        int roomTypeid = roomType.RoomTypeId;
        //        SqlParameter roomTypepam = new SqlParameter("@roomTypeid", roomTypeid);


        //        readCommand.Parameters.Add(roomTypepam);
        //        SqlParameter startDatepam = new SqlParameter("@StartDate", startdate);
        //        readCommand.Parameters.Add(startDatepam);
        //        SqlParameter endDatepam = new SqlParameter("@EndDate", enddate);
        //        readCommand.Parameters.Add(endDatepam);
        //        con.Open();
        //        SqlDataReader RoomTypeReader = readCommand.ExecuteReader();
        //        while (RoomTypeReader.Read())
        //        {
        //            foundRoomavailability = foundRoomavailability + RoomTypeReader.GetInt32(RoomTypeReader.GetOrdinal("amount"));
        //        }
        //        return foundRoomavailability;
        //    }
        //}

        public int GetRoomTypeavailability(RoomType roomType, List<DateTime> dates)
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

            using (SqlConnection con = new SqlConnection(_connectionString))
            using (SqlCommand readCommand = new SqlCommand(queryString, con))
            {
                readCommand.Parameters.AddWithValue("@roomTypeid", roomType.RoomTypeId);
                readCommand.Parameters.AddWithValue("@StartDate", startdate);
                readCommand.Parameters.AddWithValue("@EndDate", enddate);

                con.Open();
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

        public bool CheckRoomTypeId(int roomTypeId)
        {
            string queryString = "SELECT COUNT(1) FROM RoomTypes WHERE RoomTypeId = @roomTypeId";
            using (SqlConnection con = new SqlConnection(_connectionString))
            using (SqlCommand cmd = new SqlCommand(queryString, con))
            {
                try
                {
                    cmd.Parameters.Add(new SqlParameter("@roomTypeId", roomTypeId));
                    con.Open();
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            int count = reader.GetInt32(0);
                            return count > 0;
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
                catch (SqlException ex)
                {
                    Console.WriteLine($"SQL Error: {ex.Message}");
                    return false;
                }
            }
        }
    }
}
