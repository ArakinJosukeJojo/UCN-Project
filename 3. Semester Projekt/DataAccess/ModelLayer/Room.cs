using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.ModelLayer
{
    public class Room
    {
        public Room() { }
        public Room(String roomNo, string roomInfo, int roomTypeId)
        {
            RoomNo = roomNo;
            RoomInfo = roomInfo;
            RoomTypeId = roomTypeId;
        }

        public String RoomNo { get; set; }
        public string RoomInfo { get; set; }
        public int RoomTypeId { get; set; }
        public int? ReservationNo { get; set; } // Nullable to allow for rooms that are not reserved
    }
}
