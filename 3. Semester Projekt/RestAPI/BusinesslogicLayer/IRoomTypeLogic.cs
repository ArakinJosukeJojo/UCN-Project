using RestAPI.DTOs;

namespace RestAPI.BusinesslogicLayer
{
    public interface IRoomTypeLogic
    {
        public List<SmallRoomTypeDTO> GetAllRoomTypesSmall();
    }
}
