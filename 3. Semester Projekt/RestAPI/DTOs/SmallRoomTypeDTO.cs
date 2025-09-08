namespace RestAPI.DTOs
{
    public class SmallRoomTypeDTO
    {
       
      
        public string Description { get; set; }
        public int BedSpace { get; set; }
        public string roomTypeName { get; set; }
        //public string Code { get; set; }
        public Decimal Price { get; set; }
        public int roomTypeId { get; set; }
        public SmallRoomTypeDTO(int RoomTypeId, string description, int bedSpace, string RoomTypeName, decimal price) 
        {
            roomTypeId = RoomTypeId;
         
            Description = description;
            BedSpace = bedSpace;
            roomTypeName = RoomTypeName;
            Price = price;
        }
    }
}
