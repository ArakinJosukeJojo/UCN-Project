using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DesktopClient.ModelLayer
{
    public class ReservationLine
    {
        public RoomType RoomType { get; set; }
        public int Amount { get; set; }

        public ReservationLine()
        {

        }

        public ReservationLine(RoomType roomType, int amount)
        {
            RoomType = roomType;
            Amount = amount;
        }
    }
}
