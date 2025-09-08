using RestAPI.DTOs;
using DataAccess.DatabaseLayer;
using DataAccess.ModelLayer;
using RestAPI.ModelConversion;
namespace RestAPI.BusinesslogicLayer
{
    public class RoomLogic : IRoomLogic
    {
        private readonly IRoomAccess? _roomAccess;
        private readonly IRoomtypeAccess? _roomTypeAccess;
        private readonly RoomDTOConversion? _roomDTOConversion;
        public RoomLogic(IRoomAccess roomAccess, IRoomtypeAccess roomTypeAccess) 
        {
            _roomAccess = roomAccess;
            _roomTypeAccess = roomTypeAccess;
            _roomDTOConversion = new RoomDTOConversion();
        }



        public bool CreateRoom(RoomDTO roomDTO)
        {
            if (roomDTO != null)
            {
                Room room = _roomDTOConversion.FromRoomDTODesktop(roomDTO);
                return _roomAccess.CreateRoom(room);
            }
            else
            {
                throw new Exception("Room invalid - debug for further investigation");
            }
        }

        public bool DeleteRoomByRoomNo(String roomNo)
        {            
            if (roomNo != null)
            {
                return _roomAccess.DeleteRoom(roomNo);
            }
            else
            {
                throw new Exception("RoomNumber must not be null");
            }
        }

        public RoomDTO GetRoomByRoomNo(String roomNo)
        {
            if (roomNo != null)
            {
                Room room = _roomAccess.GetRoom(roomNo);
                if (room != null)
                {
                    List<RoomType> roomTypes = _roomTypeAccess.GetAllRoomTypes();
                    RoomDTO roomDTO = _roomDTOConversion.ToRoomDTODesktop(room, roomTypes);
                    return roomDTO;
                }
                else
                {
                    throw new Exception("Room not found");
                }
            }
            else
            {
                throw new Exception("RoomNumber must not be null");
            }
        }

        public bool UpdateRoom(RoomDTO roomDTO)
        {
            if (roomDTO != null)
            {
                Room room = _roomDTOConversion.FromRoomDTODesktop(roomDTO);
                return _roomAccess.UpdateRoom(room);
            }
            else
            {
                throw new Exception("Room invalid - an update was invalid");
            }
        }
    }
}
