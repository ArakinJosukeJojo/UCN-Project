using RestAPI.DTOs;

namespace RestAPI.BusinesslogicLayer
{
    public interface IReservationLogic
    {
        bool PostReservation(ReservationDTO reservationDTO);
    }
}
