using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DesktopClient.ModelLayer;
using RestAPI.DTOs;

namespace DesktopClient.BusinessLogicLayer
{
    public interface IReservationLogic
    {
        IEnumerable<TypeQuantity>? GetAvailableRooms(List<DateTime> dates);
        List<TypeQuantity>? GetAllRooms(int id = -1);

        IEnumerable<TypeQuantity>? GetReservationsByGuestName(List<string> names);
        List<string>? CheckIn(int? reservationNo);

        List<string>? CheckOut(int? reservationNo);
        void AflysReservation(int? reservationNo);
    }
}
