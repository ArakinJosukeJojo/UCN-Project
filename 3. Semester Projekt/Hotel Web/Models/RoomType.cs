namespace Hotel_Web.Models
{
    public class RoomType
    {
        public int RoomTypeId { get; set; }
        public int Availableamount { get; set; }
        public string Description { get; set; }
        public int BedSpace { get; set; }
        public string RoomTypeName { get; set; }
        public Decimal Price { get; set; }

        public RoomType() { }

        public RoomType(int roomTypeId, int availableAmount, string description, int bedSpace, string roomTypeName, decimal price)
        {
            RoomTypeId = roomTypeId;
            Availableamount = availableAmount;
            Description = description;
            BedSpace = bedSpace;
            RoomTypeName = roomTypeName;
            Price = price;
        }
    }
}
