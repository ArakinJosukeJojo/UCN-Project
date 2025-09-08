namespace Hotel_Web.Models
{
    public class ReservationLine
    {
        public RoomType RoomType { get; set; }
        public int Amount { get; set; }

        public ReservationLine() 
        { 
        
        }

        public ReservationLine(RoomType roomType, int amount)
        {
            RoomType = roomType;
            Amount = amount;
        }
    }
}
