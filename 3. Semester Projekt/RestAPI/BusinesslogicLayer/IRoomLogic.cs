using DataAccess.ModelLayer;
using RestAPI.DTOs;

namespace RestAPI.BusinesslogicLayer
{
    public interface IRoomLogic
    {
        public RoomDTO GetRoomByRoomNo(String roomNo);

        public bool DeleteRoomByRoomNo(String roomNo);

        public bool CreateRoom(RoomDTO room);

        public bool UpdateRoom(RoomDTO room);
       



    }
}
