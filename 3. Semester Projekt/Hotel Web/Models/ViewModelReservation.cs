namespace Hotel_Web.Models
{
    public class ViewModelReservation
    {
        public List<RoomType> RoomTypes { get; set; }
      
        public Reservation Reservation {  get; set; }
        public ViewModelReservation()
        {
            RoomTypes = new List<RoomType>();
            Reservation = new Reservation();
        }
    }
}
