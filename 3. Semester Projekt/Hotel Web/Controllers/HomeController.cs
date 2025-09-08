using System.Diagnostics;
using System.Diagnostics.Eventing.Reader;
using System.Globalization;
using Hotel_Web.Models;
using Microsoft.AspNetCore.Mvc;
using Hotel_Web.Service;
using System.Collections.Generic;
using Hotel_Web.NewFolder;
using Hotel_Web.BusinesslogicLayer;
using System.Security.Cryptography.X509Certificates;
using Microsoft.AspNetCore.Http;
using Newtonsoft.Json;

namespace Hotel_Web.Controllers
{

    public class HomeController : Controller

    {
        IReservationServiceAccess _reservationService = new ReservationServiceAccess();
        private readonly ILogger<HomeController> _logger;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        public IActionResult Booking()
        {
            return View();
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult AboutUs()
        {
            return View();
        }


        //Check if necessary
        //public IActionResult Info()
        //{
        //    return PartialView();
        //}

        [HttpPost]
        public IActionResult ShowAvailableRooms(ViewModelReservation view)
        {
            if (view != null)
            {
                return View(view);
            }
            else
            {
                ViewData["Message"] = $"fejl i ShowAvailableRooms";
                return View("Index");
            }
        }

        [HttpPost]
        public IActionResult CreateReservation(ViewModelReservation reservation)
        {
            string? cookieDataJson = Request.Cookies["Reservation"];
            Reservation currentReservation = JsonConvert.DeserializeObject<Reservation>(cookieDataJson);

            if (reservation != null && currentReservation != null)
            {
                currentReservation.ReservationLines = reservation.Reservation.ReservationLines
                    .Where(line => line.Amount > 0)
                    .ToList();
                Response.Cookies.Append("Reservation", JsonConvert.SerializeObject(currentReservation));
                return View(currentReservation);
            }
            else
            {
                ViewData["Message"] = $"fejl i CreateReservation";
                return View("Index");
            }
        }

        







        //Code for handlind the datarange input
        [HttpPost]
        // In Booking view name is: <input type="text" name="daterange" value="01/01/2025 - 01/15/2025" />
        // therefore, as name is daterange, the parameter is also daterange. MUST MATCH!
        public IActionResult Search(string daterange)
        {
            ViewModelReservation viewModelReservation;
            //checks if datarange exists
            if (string.IsNullOrWhiteSpace(daterange))
            {
                ViewData["Message"] = "Date range was not provided.";
                return View("Index"); // vis siden igen med fejlbesked
            }
            // As daterange is a string with format: "dd/mm/yyyy - dd/mm/yyyy", we can split into two dates
            //Splits the date string into 2, through seperator. Also takes parameter. In this case it removes empty entries from the array.
            string[] dates = daterange.Split(" - ", StringSplitOptions.RemoveEmptyEntries);
            
            DateTime startDate;
            DateTime endDate;
            
            string expectedFormat = "MM/dd/yyyy";

            //checks if two dates exist, and then parces them into datetimes.
            //Trim = removing whitespace.
            //expectedformat as defined above. 
            //CultureInfo.InvariantCulture = ensures that the date format is not dependent on the culture of the machine running the code.
            //DateTimeStyles.None = no special styles are applied to the parsing.
            //If successfull, startDate and endDate will be set to the parsed values.

            if (dates.Length == 2 &&
                //I if conditionen tjekker vi om der er to datoer i arrayet, og om de kan parses til DateTime objekter
                DateTime.TryParseExact(dates[0].Trim(), expectedFormat, CultureInfo.InvariantCulture, DateTimeStyles.None, out startDate) &&
                DateTime.TryParseExact(dates[1].Trim(), expectedFormat, CultureInfo.InvariantCulture, DateTimeStyles.None, out endDate))
            {
                

                List<DateTime> datadates = new List<DateTime>();
                datadates.Add(startDate);
                datadates.Add(endDate);

                // Gives input to next view, and sets a cookie for saving info.

                viewModelReservation = _reservationService.GetAvailability(datadates);
                if (viewModelReservation != null)
                {                    
                    Reservation cookieReservation = viewModelReservation.Reservation;
                    Response.Cookies.Append("Reservation", JsonConvert.SerializeObject(cookieReservation));

                    return View("ShowAvailableRooms", viewModelReservation);
                }
                else
                {
                    ViewData["Message"] = $"fejl i Booking";
                    return View("Index");
                }
            }
            else
            {
                //If the parse fails, we return to the front page with and error message.
                ViewData["Message"] = $"Invalid date range format submitted. Expected format '{expectedFormat} - {expectedFormat}'. Received: '{daterange}'";
                return View("Booking");
            }

        }

        [HttpPost]


        public IActionResult Confirmation(Reservation reservation)
        {
            {
                string? cookieDataJson = Request.Cookies["Reservation"];
                Reservation currentReservation = JsonConvert.DeserializeObject<Reservation>(cookieDataJson);

                if (reservation != null && currentReservation != null)
                {
                    currentReservation.Person = reservation.Person;
                    currentReservation.ReservationNote = "ingen note";
                    Response.Cookies.Append("Reservation", JsonConvert.SerializeObject(currentReservation));
                    return View(currentReservation);
                }
                else
                {
                    ViewData["Message"] = $"fejl i CreateReservation";
                    return View("Index");
                }
            }

        }


        [HttpPost]
        public IActionResult Payment()
        {

            string? cookieDataJson = Request.Cookies["Reservation"];
            Reservation currentReservation = JsonConvert.DeserializeObject<Reservation>(cookieDataJson);

            if (currentReservation != null)
            {
                return View(currentReservation);
            }
            else
            {
                ViewData["Message"] = $"fejl i Confirmation";
                return View("Index");
            }
        }

        public IActionResult cancel(Reservation reservation)
        {
            return View("Index");
        }


        // fejl håndtering mangler
        [HttpPost]
        public IActionResult SubmitReservation(Reservation reservation)
        {
            string? cookieDataJson = Request.Cookies["Reservation"];
            Reservation currentReservation = JsonConvert.DeserializeObject<Reservation>(cookieDataJson);
            if (currentReservation != null && reservation != null)
            {
                currentReservation.PayAtLocation = reservation.PayAtLocation;
                if(currentReservation.PayAtLocation == true)
                {
                    currentReservation.IsPaid = false;
                } else
                {
                    currentReservation.IsPaid = true;
                }
                try
                {
                    _reservationService.SubmitReservation(currentReservation);
                }
                catch (Exception ex) 
                {
                    Response.Cookies.Delete("Reservation");
                    ViewData["Message"] = $"database fejl Prøv venligst igen";
                    return View("Index");
                }
            }
            Response.Cookies.Delete("Reservation");
            ViewData["Message"] = $"din resavation er nu gemmemført hav en god dag";
            return View("Index");
        }
    }
}
