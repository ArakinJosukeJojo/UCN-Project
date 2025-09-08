namespace RestAPI.DTOs
{
    public class ViewModelReservationDTO
    {
        public List<RoomTypeDTO> RoomTypes { get; set; }       
        public ReservationDTO Reservation { get; set; }
        public ViewModelReservationDTO()
        {
            RoomTypes = new List<RoomTypeDTO>();            
            Reservation = new ReservationDTO();
        }
        public ViewModelReservationDTO(IEnumerable<RoomTypeDTO> roomTypes, DateTime startDate, DateTime endDate)
        {
            RoomTypes = (List<RoomTypeDTO>)roomTypes;
            Reservation = new ReservationDTO();
            Reservation.ReservationStartDate = startDate;
            Reservation.ReservationEndDate = endDate;
        }
    }
}
