using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DesktopClient.ModelLayer;


namespace DesktopClient.ServiceLayer
{
    public interface IReservationService
    {
        IEnumerable<TypeQuantity>? GetAllDates(List<DateTime> dates);
        List<TypeQuantity>? GetAllRooms(int id = -1);

        IEnumerable<Reservation>? GetReservation(List<DateTime> dates);

        IEnumerable<TypeQuantity>? GetReservationsByGuestName(List<string> names);
        List<string>? CheckIn(int? reservationNo);

        List<string>? CheckOut(int? reservationNo);
        bool AflysReservation(int? reservationNo);

        Room GetRoomByRoomNo(int RoomNo);

       
    }
}
