using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using DesktopClient.ModelLayer;
using Microsoft.VisualBasic.ApplicationServices;
using static Azure.Core.HttpHeader;
using System.Net.Http.Json;
using DataAccess.DatabaseLayer;


namespace DesktopClient.ServiceLayer
{
    public class ReservationService : ServiceConnection, IReservationService
    {
        public ReservationService() : base("https://localhost:7047/api/Desktop/")
        {

        }

        public IEnumerable<Reservation>? GetReservation(List<DateTime> dates)
        {
            var content = new StringContent(JsonConvert.SerializeObject(dates), null, "application/json");
            var response = base.CallServicePost(content);
            response.EnsureSuccessStatusCode();
            var result = response.Content.ReadAsStringAsync().Result;
            List<AvailabilityJsonConvert> convertList = JsonConvert.DeserializeObject<List<AvailabilityJsonConvert>>(result);

            List<Reservation> reservation = new List<Reservation>();
            foreach (var item in convertList)
            {

            }

            return reservation;
        }


        public List<TypeQuantity>? GetAllRooms(int id = -1)
        {

            List<TypeQuantity>? reservationFromService = null;
            UseUrl = BaseUrl + "GetReservation";
            bool oneReservationById = (id > 0);

            //if (oneReservationById)
            //{
            //    UseUrl += id;
            //}

            try
            {
                var serviceResponse =  base.CallServiceGet();
                if (serviceResponse != null && serviceResponse.IsSuccessStatusCode)
                {
                    if (serviceResponse.StatusCode == HttpStatusCode.OK)
                    {
                        string responseData = serviceResponse.Content.ReadAsStringAsync().Result;
                        if (true)
                        {
                            reservationFromService = JsonConvert.DeserializeObject<List<TypeQuantity>>(responseData);

                            //TypeQuantityDTO? foundReservation = JsonConvert.DeserializeObject<TypeQuantityDTO>(responseData);
                            //if (foundReservation != null)
                            //{
                            //    Console.WriteLine("Vi er i reservationConfirmation. foundReservation er ikke null");

                            //    reservationFromService = new List<TypeQuantityDTO> {foundReservation};

                            //}
                            //else
                            //{
                            //    reservationFromService = JsonConvert.DeserializeObject<List<TypeQuantityDTO>>(responseData);
                            //}
                        }
                        else if (serviceResponse.StatusCode == HttpStatusCode.NoContent)
                        {
                            reservationFromService = new List<TypeQuantity>();
                        }
                    }
                }
            }
            catch
            {
                reservationFromService = null;
            }
            return reservationFromService;
        }

        public IEnumerable<TypeQuantity> GetAllDates(List<DateTime> dates)
        {
            List<TypeQuantity>? dtoList = null;
            UseUrl = BaseUrl + "GetReservationByDate";

            try
            {
                // Serialize dates to JSON
                var json = JsonConvert.SerializeObject(dates);
                var content = new StringContent(json, Encoding.UTF8, "application/json");

                // Now call your method correctly
                var serviceResponse = base.CallServicePost(content); // .Result is already used inside

                if (serviceResponse != null && serviceResponse.IsSuccessStatusCode)
                {
                    string responseData = serviceResponse.Content.ReadAsStringAsync().Result;
                    dtoList = JsonConvert.DeserializeObject<List<TypeQuantity>>(responseData);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error in GetAllDates: " + ex.Message);
            }

            return dtoList;
        }


        public IEnumerable<TypeQuantity>? GetReservationsByGuestName(List<string> names)
        {
            List<TypeQuantity>? dtoList = null;
            UseUrl = BaseUrl + "GetReservationByName";

            try
            {
                // Serialize dates to JSON
                var json = JsonConvert.SerializeObject(names);
                var content = new StringContent(json, Encoding.UTF8, "application/json");

                // Now call your method correctly
                var serviceResponse = base.CallServicePost(content); // .Result is already used inside

                if (serviceResponse != null && serviceResponse.IsSuccessStatusCode)
                {
                    string responseData = serviceResponse.Content.ReadAsStringAsync().Result;
                    dtoList = JsonConvert.DeserializeObject<List<TypeQuantity>>(responseData);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error in GetAllDates: " + ex.Message);
            }
            return dtoList;
        }

        
        public Room GetRoomByRoomNo(int RoomNo)
        {
            Room? room = null;
            try
            {
                UseUrl = BaseUrl + "GetRoomByNo/" + RoomNo.ToString();

                //var json = JsonConvert.SerializeObject(RoomNo);
                //var content = new StringContent(json, Encoding.UTF8, "application/json");
                var serviceResponse = base.CallServiceGet();
                if (serviceResponse != null && serviceResponse.IsSuccessStatusCode)
                {
                    string responseData = serviceResponse.Content.ReadAsStringAsync().Result;
                    room = JsonConvert.DeserializeObject<Room>(responseData);

                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error in GetAllDates: " + ex.Message);
            }
            return room;
        }

        public List<string>? CheckIn(int? reservationNo)
        {
            try
            {
                UseUrl = BaseUrl + "CheckIn";

                var content = new StringContent(JsonConvert.SerializeObject(reservationNo), null, "application/json");
                var response = base.CallServicePost(content);
                response.EnsureSuccessStatusCode();

                var result = response.Content.ReadAsStringAsync().Result;

                // Deserialize list of room numbers from the response
                var roomNumbers = JsonConvert.DeserializeObject<List<string>>(result);
                return roomNumbers;
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error during check-in: " + ex.Message);
                return null;
            }
        }

        public List<string>? CheckOut(int? reservationNo)
        {
            try
            {
                UseUrl = BaseUrl + "CheckOut";

                var content = new StringContent(JsonConvert.SerializeObject(reservationNo), null, "application/json");
                var response = base.CallServicePost(content);
                response.EnsureSuccessStatusCode();
                var result = response.Content.ReadAsStringAsync().Result;

                var roomNumbers = JsonConvert.DeserializeObject<List<string>>(result);
                return roomNumbers;
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error during check-out: " + ex.Message);
                return null;
            }
            
        }

        public bool AflysReservation(int? reservationNo)
        {
            try
            {
                if (reservationNo == null)
                    return false;

                UseUrl = $"{BaseUrl}AflysReservation/{reservationNo}";

                var response = base.CallServiceDelete(); // <- You need to implement or use DELETE here
                response.EnsureSuccessStatusCode();

                return true;
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error during cancellation: " + ex.Message);
                return false;
            }
        }
    }
}

