using Hotel_Web.Models;

namespace Hotel_Web.Service
{
    public interface IDateService
    {
        ViewModelReservation? GetAvailiability(List<DateTime> dates);

        void SubmitReservation(Reservation reservation);

    }
}
