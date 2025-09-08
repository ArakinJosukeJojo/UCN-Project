using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DesktopClient.ModelLayer
{
    public class ViewModelReservation
    {
        public List<RoomType> RoomTypes { get; set; }
        public List<ReservationLine> ReservationLines { get; set; }
        public Reservation Reservation { get; set; }
        public ViewModelReservation()
        {
            RoomTypes = new List<RoomType>();
            ReservationLines = new List<ReservationLine>();
            Reservation = new Reservation();
        }
    }
}
