using DataAccess.ModelLayer;
using DataAccess.DatabaseLayer;
using RestAPI.DTOs;
namespace RestAPI.ModelConversion
{
    public class RoomDTOConversion
    {

        public RoomDTOConversion()
        {

        }

        public RoomDTO ToRoomDTODesktop(Room room, List<RoomType> roomTypes)
        {

            if (room != null)
            {
                RoomDTO roomDTO = new RoomDTO();

                roomDTO.RoomNo = room.RoomNo;
                roomDTO.RoomInfo = room.RoomInfo;
                roomDTO.roomTypeId = room.RoomTypeId;
                for (int i = 0; i < roomTypes.Count; i++)
                {
                    if (room.RoomTypeId == roomTypes[i].RoomTypeId)
                    {
                        roomDTO.RoomTypeName = roomTypes[i].RoomTypeName;
                        break;
                    }
                }
                if (room.ReservationNo > 0)
                {
                    roomDTO.IsOccupied = true;
                    roomDTO.ReservationNo = room.ReservationNo;
                }
                else
                {
                    roomDTO.IsOccupied = false;
                    roomDTO.ReservationNo = 0;
                }
                return roomDTO;
            }
            else
            {
                return null;
            }
        }

        public Room FromRoomDTODesktop(RoomDTO roomDTO)
        {
            if (roomDTO != null)
            {
                Room room = new Room();
                room.RoomNo = roomDTO.RoomNo;
                room.RoomInfo = roomDTO.RoomInfo;
                room.RoomTypeId = roomDTO.roomTypeId;
                if (roomDTO.ReservationNo != 0)
                {
                    room.ReservationNo = roomDTO.ReservationNo;                  
                }
                else
                {
                    room.ReservationNo = 0;
                }
                return room;
            }
            else
            {
                return null;
            }
        }
    }
}
