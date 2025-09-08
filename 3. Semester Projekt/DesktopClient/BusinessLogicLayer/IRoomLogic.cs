using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DesktopClient.ModelLayer;

namespace DesktopClient.BusinessLogicLayer
{
    internal interface IRoomLogic
    {
        public void PostRoom(Room room);
        Room GetRoom(string RoomNo);

        void EditRoomInfo(Room room);

        List<RoomType> GetRoomTypes();

        void DeleteRoom(string roomNo);

    }
}
