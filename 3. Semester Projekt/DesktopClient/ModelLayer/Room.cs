using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DesktopClient.ModelLayer
{
    public class Room
    {
        public Room() { }
        public Room(String roomNo, string roomInfo) 
        {
            RoomNo = roomNo;
            RoomInfo = roomInfo;
        }

        public String RoomNo { get; set; }
        public string RoomInfo { get; set; }
        public bool IsOccupied { get; set; }        
        public int? roomTypeId { get; set; }
        public int? reservationNo { get; set; }
       
    }
}
