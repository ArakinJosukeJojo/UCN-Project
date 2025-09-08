using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DesktopClient.ModelLayer;

namespace DesktopClient.ServiceLayer
{
    internal interface IRoomService
    {
        public void postRoom(Room room);
        public Room GetRoomByRoomNo(string RoomNo);
        public void EditRoomInfo(Room room);
        public void DeletRoom(string RoomNo);

        public List<RoomType> GetRoomTyps();
    }
}
