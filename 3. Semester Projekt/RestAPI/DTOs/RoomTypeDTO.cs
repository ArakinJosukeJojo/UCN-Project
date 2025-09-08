using DataAccess.ModelLayer;
using RestAPI.ModelConversion;
namespace RestAPI.DTOs

{
    public class RoomTypeDTO
    {
        public RoomTypeDTO() { }
        public RoomTypeDTO(int roomTypeId) 
        {
            RoomTypeId = roomTypeId;
        }
        public RoomTypeDTO(int roomTypeId, string roomTypeName, string description, int bedSpace, Decimal price, int availability)
        {
            RoomTypeId = roomTypeId;
            RoomTypeName = roomTypeName;
            Description = description;
            BedSpace = bedSpace;
            Price = price;
            Availableamount = availability;
        }

        public int RoomTypeId { get; set; }
        public string RoomTypeName { get; set; }
        public string? Description { get; set; }

        public int? BedSpace { get; set; }
        public Decimal? Price { get; set; }
        public int? Availableamount { get; set; }        
    }
}
