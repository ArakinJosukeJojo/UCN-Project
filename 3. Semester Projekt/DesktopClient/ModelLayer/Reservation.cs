using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DesktopClient.ModelLayer
{
    public class Reservation
    {

        //public List<ReservationLine> ReservationLines { get; set; }
        public DateTime ReservationStartDate { get; set; }
        public DateTime ReservationEndDate { get; set; }
        public bool? CheckedIn { get; set; }
        public bool? CheckedOut { get; set; }
        public bool PayAtLocation { get; set; }
        public bool? IsConfirmationSent { get; set; }
        public bool? IsPaid { get; set; }
        public string ReservationNote { get; set; }
        public Person? Person { get; set; }

        public Reservation()
        {
            //   ReservationLines = new List<ReservationLine>();
        }

    }
}