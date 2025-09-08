using Hotel_Web.Models;
namespace Hotel_Web.NewFolder
{
    public interface IReservationServiceAccess
    {
        public void SubmitReservation(Reservation reservation);

        public ViewModelReservation GetAvailability(List<DateTime> datadates);

        
    }
}
