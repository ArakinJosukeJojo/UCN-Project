using Hotel_Web.NewFolder;
using Hotel_Web.Service;
using Hotel_Web.Models;

namespace Hotel_Web.BusinesslogicLayer
{
    public class ReservationServiceAccess : IReservationServiceAccess
    {
        IDateService _dateService;
        public ReservationServiceAccess() 
        {
            _dateService = new DateService();
        }
 
        public ViewModelReservation GetAvailability(List<DateTime> datadates)
        {
           
            var result = _dateService.GetAvailiability(datadates);
            return result;
        }

        public void SubmitReservation(Reservation reservation)
        {
            _dateService.SubmitReservation(reservation);
        }
    }
}
