using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataAccess.ModelLayer;
using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Configuration;


namespace DataAccess.DatabaseLayer
{
    public class HotelDataBaseAccess
    {

        private readonly string _connectionString;

        public HotelDataBaseAccess(string server, string database)
        {
            _connectionString = $"Server={server};Database={database};Integrated Security=True;Encrypt=True;TrustServerCertificate=True;";
            
        }

        public bool TestConnection()
        {
            try
            {
                using (var connection = new SqlConnection(_connectionString))
                {
                    connection.Open();
                    using (var command = new SqlCommand("SELECT 1", connection))
                    {
                        command.ExecuteScalar();
                    }
                    connection.Close();
                    return true;
                }
            }
            catch
            {
                return false;
            }
        }

        public int GetRoomTypeCount()
        {
            string query = "SELECT COUNT(*) FROM RoomTypes";

            using (var connection = new SqlConnection(_connectionString))
            {
                connection.Open();

                using (var command = new SqlCommand(query, connection))
                {
                int count = Convert.ToInt32(command.ExecuteScalar());

                connection.Close();

                return count;
                }   
            }
        }
        public List<RoomTypeBooking> GetBookedRoomsPerRoomType(int roomTypeCount, DateTime startDate, DateTime endDate)
        {
            var bookedRoomsList = new List<RoomTypeBooking>();

            using (var connection = new SqlConnection(_connectionString))
            {
                connection.Open();

                for (int roomTypeId = 1; roomTypeId <= roomTypeCount; roomTypeId++)
                {
                    string query = @"
                SELECT RT.RoomTypeId, RT.RoomTypeName, COUNT(DISTINCT RL.RoomNo) AS BookedRooms
                FROM Reservations R
                INNER JOIN ReservationsLines RL ON R.ReservationNo = RL.ReservationNo
                INNER JOIN RoomTypes RT ON RL.RoomTypeId = RT.RoomTypeId
                WHERE RL.RoomTypeId = @RoomTypeId
                  AND NOT (R.ReservationEndDate <= @StartDate OR R.ReservationStartDate >= @EndDate)
                GROUP BY RT.RoomTypeId, RT.RoomTypeName";

                    using (var command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@RoomTypeId", roomTypeId);
                        command.Parameters.AddWithValue("@StartDate", startDate);
                        command.Parameters.AddWithValue("@EndDate", endDate);

                        using (var reader = command.ExecuteReader())
                        {
                            if (reader.Read())
                            {
                                bookedRoomsList.Add(new RoomTypeBooking
                                {
                                    RoomTypeId = reader.GetInt32(0),
                                    RoomTypeName = reader.GetString(1),
                                    BookedRoomCount = reader.GetInt32(2)
                                });
                            }
                        }
                    }
                }
                connection.Close();
            }
    
            return bookedRoomsList;
        }

        public List<RoomTypeAvailability> GetAvailableRoomsPerRoomType(int roomTypeCount, DateTime startDate, DateTime endDate)
        {
            var result = new List<RoomTypeAvailability>();

            using (var connection = new SqlConnection(_connectionString))
            {
            connection.Open();

                for (int roomTypeId = 1; roomTypeId <= roomTypeCount; roomTypeId++)
                {
                    string query = @"
                SELECT rt.RoomTypeId, rt.RoomTypeName, COUNT(*) AS AvailableRoomCount
                FROM Rooms r
                JOIN RoomTypes rt ON r.RoomTypeId = rt.RoomTypeId
                WHERE r.RoomTypeId = @RoomTypeId
                AND r.RoomNo NOT IN (
                    SELECT rl.RoomNo
                    FROM ReservationsLines rl
                    JOIN Reservations res ON rl.ReservationNo = res.ReservationNo
                    WHERE rl.RoomTypeId = @RoomTypeId
                    AND NOT (
                        res.ReservationEndDate <= @StartDate OR
                        res.ReservationStartDate >= @EndDate
                    )
                )
                GROUP BY rt.RoomTypeId, rt.RoomTypeName";

                    using (var command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@RoomTypeId", roomTypeId);
                        command.Parameters.AddWithValue("@StartDate", startDate);
                        command.Parameters.AddWithValue("@EndDate", endDate);

                        using (var reader = command.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                result.Add(new RoomTypeAvailability
                                {
                                    RoomTypeId = reader.GetInt32(0),
                                    RoomTypeName = reader.GetString(1),
                                    AvailableRoomCount = reader.GetInt32(2)
                                });
                            }
                        }
                    }
                   
                }
                connection.Close();
            }

            return result;
        }


    }
}


