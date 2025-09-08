using System;
using Newtonsoft.Json;
using System.Net;
using Microsoft.Extensions.DependencyInjection;
using Hotel_Web.Models;
using System.Text;
namespace Hotel_Web.Service

{
    public class DateService : ServiceConnection, IDateService
    {
        string? fullUrl = null;
        public DateService() : base("https://localhost:7047/api/reservation/")
        {
        }

        public ViewModelReservation GetAvailiability(List<DateTime> dates)
        {
            UseUrl = BaseUrl + "Check";
            
            try
            {              
                var content = new StringContent(JsonConvert.SerializeObject(dates), Encoding.UTF8, "application/json");
                var response = base.CallServicePost(content);
                response.EnsureSuccessStatusCode();
                var result = response.Content.ReadAsStringAsync().Result;                  
                ViewModelReservation viewModelReservation = JsonConvert.DeserializeObject<ViewModelReservation>(result);

                return viewModelReservation;

            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error posting person: {ex.Message}");
            }

            return null; // Indicate failure
        }

        public void SubmitReservation(Reservation reservation)
        {
            UseUrl = BaseUrl + "PostReservation";
            Console.WriteLine("Calling URL: " + UseUrl);
            try
            {                
                var json = JsonConvert.SerializeObject(reservation);
                Console.WriteLine(json);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                var response = base.CallServicePost(content);
                response.EnsureSuccessStatusCode();
            }
            catch(Exception e)
            {
                throw new Exception($"Error posting reservation: {e.Message}");
            }
        }


       
    }
}