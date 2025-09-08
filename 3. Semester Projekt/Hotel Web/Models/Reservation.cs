namespace Hotel_Web.Models
{
    public class Reservation
    {
        public List<ReservationLine> ReservationLines { get; set; }
        public DateTime ReservationStartDate { get; set; }
        public DateTime ReservationEndDate { get; set; }
        public bool? CheckedIn {  get; set; }
        public bool? CheckedOut { get; set; }
        public bool PayAtLocation { get; set; }
        public bool? IsConfirmationSent { get; set; }
        public bool? IsPaid { get; set; }
        public string ReservationNote { get; set; }
        public Person? Person { get; set; }

        public Reservation() 
        {
            ReservationLines = new List<ReservationLine>();
        }
        public Reservation(DateTime reservationStartDate, DateTime reservationEndDate, bool? checkedIn, bool? checkedOut, bool payAtLocation, bool? isConfirmationSent, bool? isPaid, string reservationNote, Person? person)
        {
            ReservationLines = new List<ReservationLine>();       
            ReservationStartDate = reservationStartDate;
            ReservationEndDate = reservationEndDate;
            CheckedIn = checkedIn;
            CheckedOut = checkedOut;
            PayAtLocation = payAtLocation;
            IsConfirmationSent = isConfirmationSent;
            IsPaid = isPaid;
            ReservationNote = reservationNote;
            Person = person ?? new Person();
        }
    }
}
