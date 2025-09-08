using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DesktopClient.ModelLayer;
using DesktopClient.ServiceLayer;


namespace DesktopClient.BusinessLogicLayer
{
    public class ReservationLogic : IReservationLogic
    {
        IReservationService _reservationService;

        public ReservationLogic()
        {
            _reservationService = new ReservationService();
        }

        public IEnumerable<TypeQuantity>? GetAvailableRooms(List<DateTime> dates)
        {
           return _reservationService.GetAllDates(dates);
        }

        public List<TypeQuantity>? GetAllRooms(int id = -1)
        {
            return _reservationService.GetAllRooms();
        }

        public IEnumerable<TypeQuantity>? GetReservationsByGuestName(List<string> names)
        {
            return _reservationService.GetReservationsByGuestName(names);
        }

        public List<string>? CheckIn(int? reservationNo)
        {
            return _reservationService.CheckIn(reservationNo);
        }

        public List<string>? CheckOut(int? reservationNo)
        {
            return _reservationService.CheckOut(reservationNo);
        }

        public void AflysReservation(int? reservationNo)
        {
            _reservationService.AflysReservation(reservationNo);
        }

        //public ViewModelReservation GetAvailability(List<DateTime> datadates)
        //{
        //  IDateService _dateService = new DateService();
        //var result = _dateService.GetAvailiability(datadates);
        //return result;
        //}
    }
}
