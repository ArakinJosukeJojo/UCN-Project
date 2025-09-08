namespace RestAPI.DTOs
{
    public class ReservationDTO
    {
        public List<ReservationLineDTO> ReservationLines { get; set; }
        public DateTime ReservationStartDate { get; set; }
        public DateTime ReservationEndDate { get; set; }
        public bool? CheckedIn { get; set; }
        public bool? CheckedOut { get; set; }
        public bool PayAtLocation { get; set; }
        public bool? IsConfirmationSent { get; set; }
        public bool? IsPaid { get; set; }
        public string? ReservationNote { get; set; }
        public PersonDTO? Person { get; set; }
       
        public ReservationDTO()
        {
            ReservationLines = new List<ReservationLineDTO>();
        }
        public ReservationDTO(DateTime reservationStartDate, DateTime reservationEndDate, bool payAtLocation, bool? isPaid, string? reservationNote, List<ReservationLineDTO> reservationLineDTOs, PersonDTO person)
        {
            ReservationStartDate = reservationStartDate;
            ReservationEndDate = reservationEndDate;
            PayAtLocation = payAtLocation;
            IsPaid = isPaid;
            ReservationNote = reservationNote;
            ReservationLines = reservationLineDTOs;  
            Person = person;
        }
    }
}
