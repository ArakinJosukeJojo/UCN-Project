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


namespace DesktopClient.ServiceLayer
{
    internal class RoomService : ServiceConnection, IRoomService
    {
        public RoomService() : base("https://localhost:7047/api/Desktop/") 
        { 
        
        
        }
        public void postRoom(Room room)
        {
            UseUrl = BaseUrl + "CreateRoom";
            try
            {
                var content = new StringContent(JsonConvert.SerializeObject(room), null, "application/json");
                var response = base.CallServicePost(content);
                response.EnsureSuccessStatusCode();
            }
            catch (Exception ex)
            {
                throw new Exception($"Error posting Room: {ex.Message}");
            }

        }
        public Room GetRoomByRoomNo(string RoomNo)
        {
            Room? room = null;
            try
            {
                UseUrl = BaseUrl + "GetRoomByNo/" + RoomNo.ToString();

              
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


        public void EditRoomInfo(Room room)
        {
            UseUrl = BaseUrl + "UpdateRoom";
            try
            {
                var content = new StringContent(JsonConvert.SerializeObject(room), null, "application/json");
                var response = base.CallServicePut(content);
                response.EnsureSuccessStatusCode();
            }
            catch (Exception ex)
            {
                throw new Exception($"Error posting Room: {ex.Message}");
            }

        }

        public void DeletRoom(string RoomNo)
        {
            UseUrl = BaseUrl + "DeleteRoomByRoomNo/" + RoomNo;
            try
            {
               
                var response = base.CallServiceDelete();
                response.EnsureSuccessStatusCode();
            }
            catch (Exception ex)
            {
                throw new Exception($"Error posting Room: {ex.Message}");
            }

        }

        public List<RoomType> GetRoomTyps()
        {

            List<RoomType> rooms = null;
            try
            {
                UseUrl = BaseUrl + "GetAllRoomTypes";



                var response = base.CallServiceGet();
                if (response != null && response.IsSuccessStatusCode)
                {
                    string responseData = response.Content.ReadAsStringAsync().Result;
                    rooms = JsonConvert.DeserializeObject<List<RoomType>>(responseData);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error in GetAllDates: " + ex.Message);
            }

            return rooms;

        }
    }
    



    
}
