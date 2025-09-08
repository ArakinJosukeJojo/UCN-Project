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
    public class RoomAccess : IRoomAccess
    {
        private readonly string? _connectionString;
        private readonly RoomtypeAccess _roomTypeAccess;
        public RoomAccess(IConfiguration inConfig)
        {
            _connectionString = inConfig.GetConnectionString("DatabaseConnection");
            _roomTypeAccess = new RoomtypeAccess(inConfig);
        }

        // For test (convenience)
        public RoomAccess(string inConnectionString)
        {
            _connectionString = inConnectionString;
        }
        public RoomAccess() { }

        public int GetAvailableRoom(int roomId)
        {
            int foundAvailability = 0;
            string queryString = "SELECT count (*) FROM Rooms WHERE roomTypeId = @workingRoomTypeID";
            using (SqlConnection con = new SqlConnection(_connectionString))
            using (SqlCommand readCommand = new SqlCommand(queryString, con))
            {
                SqlParameter roomIDParam = new SqlParameter("@workingRoomTypeID", roomId);
                readCommand.Parameters.Add(roomIDParam);

                con.Open();

                using (SqlDataReader personReader = readCommand.ExecuteReader())
                {
                    if (personReader.Read())  // Ensure there's at least one row (should be true for this query)
                    {
                        foundAvailability = personReader.GetInt32(0); // Read the first column, which is the count
                    }
                }
            }
            return foundAvailability;
        }


        public bool CreateRoom(Room room)
        {
            if (_roomTypeAccess.CheckRoomTypeId(room.RoomTypeId))
            {

                string queryString = "INSERT INTO Rooms (RoomNo, RoomInfo, IsOccupied, RoomTypeId, ReservationNo) VALUES (@roomNo, @roomInfo, 0, @roomTypeId, null)";
                using (SqlConnection con = new SqlConnection(_connectionString))
                using (SqlCommand cmd = new SqlCommand(queryString, con))
                {
                    try
                    {
                        cmd.Parameters.AddRange(new[] {
                                new SqlParameter("@roomNo", room.RoomNo),
                                new SqlParameter("@roomInfo", room.RoomInfo),
                                new SqlParameter("@roomTypeId", room.RoomTypeId),
                            });
                        con.Open();
                        int rowsAffected = cmd.ExecuteNonQuery();
                        return rowsAffected > 0;
                    }
                    catch (SqlException ex)
                    {
                        Console.WriteLine($"SQL Error: {ex.Message}");
                        return false;
                    }
                }
            }
            else
            {
                Console.WriteLine("RoomTypeId not valid");
                return false;
            }
        }
        public Room GetRoom(String roomNo)
        {
            string queryString = "SELECT RoomNo, RoomInfo, RoomTypeId, ReservationNo FROM Rooms WHERE RoomNo = @roomNo";

            try
            {
                using (SqlConnection con = new SqlConnection(_connectionString))
                using (SqlCommand cmd = new SqlCommand(queryString, con))
                {
                    cmd.Parameters.Add(new SqlParameter("@roomNo", roomNo));
                    con.Open();

                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Room
                            {
                                RoomNo = reader.GetString(reader.GetOrdinal("RoomNo")),
                                RoomInfo = reader.GetString(reader.GetOrdinal("RoomInfo")),
                                RoomTypeId = reader.GetInt32(reader.GetOrdinal("RoomTypeId")),
                                ReservationNo = reader.IsDBNull(reader.GetOrdinal("ReservationNo"))
                                    ? null
                                    : reader.GetInt32(reader.GetOrdinal("ReservationNo"))
                            };
                        }
                    }
                }
            }
            catch (SqlException ex)
            {
                Console.WriteLine($"SQL Error: {ex.Message}");
            }

            return null; // Not found or error
        }

        public bool UpdateRoom(Room room) 
        {
            if (_roomTypeAccess.CheckRoomTypeId(room.RoomTypeId))
            {

                string queryString = "UPDATE Rooms SET RoomInfo = @roomInfo, RoomTypeId = @roomTypeId WHERE RoomNo = @roomNo";
                using (SqlConnection con = new SqlConnection(_connectionString))
                using (SqlCommand cmd = new SqlCommand(queryString, con))
                {
                    try
                    {
                        cmd.Parameters.AddRange(new[] {
                            new SqlParameter("@roomNo", room.RoomNo),
                            new SqlParameter("@roomInfo", room.RoomInfo),
                            new SqlParameter("@roomTypeId", room.RoomTypeId),
                        });
                        con.Open();
                        int rowsAffected = cmd.ExecuteNonQuery();
                        return rowsAffected > 0;
                    }
                    catch (SqlException ex)
                    {
                        Console.WriteLine($"SQL Error: {ex.Message}");
                        return false;
                    }
                }
            }
            else
            {
                Console.WriteLine("RoomTypeId not valid");
                return false;
            }
        }
        public bool DeleteRoom(String roomNo) 
        { 
            string queryString = "DELETE FROM Rooms WHERE RoomNo = @roomNo";
            using (SqlConnection con = new SqlConnection(_connectionString))
            using (SqlCommand cmd = new SqlCommand(queryString, con))
            {
                try
                {
                    cmd.Parameters.Add(new SqlParameter("@roomNo", roomNo));
                    con.Open();
                    int rowsAffected = cmd.ExecuteNonQuery();
                    return rowsAffected > 0;
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
