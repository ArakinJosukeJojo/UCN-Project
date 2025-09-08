using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DesktopClient.ModelLayer;
using DesktopClient.ServiceLayer;
using RestAPI.DTOs;

namespace DesktopClient.BusinessLogicLayer
{
    internal class RoomLogic : IRoomLogic
    {
        RoomService _roomService;
        public RoomLogic() 
        {
            _roomService= new RoomService();
        }

        public void PostRoom(Room room)
        {
            _roomService.postRoom(room);

        }
        public Room GetRoom(string RoomNo) 
        {
          Room room = _roomService.GetRoomByRoomNo(RoomNo);
            return  room;
        }


        internal Room GetRoom(Func<string> toString)
        {
            throw new NotImplementedException();
        }
        public void EditRoomInfo(Room room)


        {
            _roomService.EditRoomInfo(room);

        }
        public void DeleteRoom(string roomNo)
        {
            _roomService.DeletRoom(roomNo);

        }

        public List<RoomType> GetRoomTypes()
        {
            List<RoomType> roomTypes = _roomService.GetRoomTyps();
            return roomTypes;
        }
    }
}
