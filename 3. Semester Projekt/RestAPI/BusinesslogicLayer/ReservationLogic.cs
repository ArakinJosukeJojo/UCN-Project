
using DataAccess.DatabaseLayer;
using DataAccess.ModelLayer;
using RestAPI.DTOs;
using RestAPI.ModelConversion;


namespace RestAPI.BusinesslogicLayer
{
    public class ReservationLogic : IReservationLogic
    {
        private readonly IReservationAccess _reservationAccess;
        private readonly ReservationDTOConvert _reservationDTOConvert;
        public ReservationLogic(IReservationAccess reservationAccess)
        {
            _reservationAccess = reservationAccess;
            _reservationDTOConvert = new ReservationDTOConvert();
        }
        public bool PostReservation(ReservationDTO reservationDTO)
        {
            Reservation reservation = _reservationDTOConvert.ToReservation(reservationDTO);
            bool result;
            if (reservation != null)
            {
                result = _reservationAccess.Transaction(reservation);
            } else
            {
                result = false;
            }
            return result;
        }
    }
}
