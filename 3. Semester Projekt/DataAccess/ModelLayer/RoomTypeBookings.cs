using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.ModelLayer
{
    public class RoomTypeBooking
    {
        public int RoomTypeId { get; set; }
        public string RoomTypeName { get; set; }
        public int BookedRoomCount { get; set; }
    }
}
