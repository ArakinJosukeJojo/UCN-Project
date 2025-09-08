using DataAccess.DatabaseLayer;
using RestAPI.DTOs;
using RestAPI.ModelConversion;

namespace RestAPI.BusinesslogicLayer
{
    public class BookingLogic : IBookingLogic
    {
        private readonly IRoomtypeAccess? _roomtypeAccess;
        private readonly IRoomAccess? _roomAccess;
        private readonly IBookingAccess _bookingAccess;

        public BookingLogic(IBookingAccess bookingAccess)
        {
            _bookingAccess = bookingAccess;
        }

        public List<string>? CheckIn(int checkIn)
        {
            try
            {
                return _bookingAccess.CheckIn(checkIn); // returns list of assigned room numbers
            }
            catch (Exception ex)
            {
                Console.WriteLine("Check-in failed: " + ex.Message);
                return null;
            }
        }

        public List<string>? CheckOut(int checkOut)
        {

            try
            {
                return _bookingAccess.CheckOut(checkOut); // returns list of assigned room numbers
            }
            catch (Exception ex)
            {
                Console.WriteLine("Check-in failed: " + ex.Message);
                return null;
            }
        }

        public bool AflysReservation(int reservationsNo)
        {
            return _bookingAccess.AflysReservation(reservationsNo);
        }
    }
}
