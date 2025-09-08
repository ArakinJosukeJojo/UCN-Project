using DataAccess.DatabaseLayer;
using DataAccess.ModelLayer;
using Microsoft.Identity.Client;
using RestAPI.DTOs;
using RestAPI.ModelConversion;

namespace RestAPI.BusinesslogicLayer
{
    public class RoomTypeLogic: IRoomTypeLogic
    {
        RoomtypeAccess? _roomtypeAccess;
        RoomtypeDTOConvert? _roomtypeDTOConvert;
        public RoomTypeLogic(IConfiguration icon) {
            _roomtypeAccess = new RoomtypeAccess(icon.GetConnectionString("DatabaseConnection"));
            _roomtypeDTOConvert = new RoomtypeDTOConvert();
        }

        public List<SmallRoomTypeDTO> GetAllRoomTypesSmall()
        {
            List<SmallRoomTypeDTO> r =new List<SmallRoomTypeDTO>();
            List<RoomType> roomTypes = _roomtypeAccess.GetAllRoomTypes();
            foreach (RoomType roomType in roomTypes)
            {
                r.Add(_roomtypeDTOConvert.ToSmallRoomTypeDTO(roomType));
            }

            return r;
        }
    }
}
