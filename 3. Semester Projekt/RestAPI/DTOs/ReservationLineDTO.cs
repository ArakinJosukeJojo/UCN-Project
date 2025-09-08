namespace RestAPI.DTOs
{
    public class ReservationLineDTO
    {
        public ReservationLineDTO() { }
        public ReservationLineDTO(RoomTypeDTO roomType, int amount)
        {
            RoomType = roomType;
            Amount = amount;
        }

        public RoomTypeDTO RoomType { get; set; }
        public int Amount { get; set; }
    }
}
