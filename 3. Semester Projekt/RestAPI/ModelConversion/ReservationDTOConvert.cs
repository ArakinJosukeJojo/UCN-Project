using DataAccess.ModelLayer;
using RestAPI.DTOs;

namespace RestAPI.ModelConversion
{
    public class ReservationDTOConvert
    {
        public ReservationDTOConvert() { }

        public Reservation ToReservation(ReservationDTO reservation)
        {
            List<ReservationLine> reservationLines = new List<ReservationLine>();
            foreach (ReservationLineDTO r in reservation.ReservationLines) {
                RoomType roomType = new RoomType();
                roomType.RoomTypeId = r.RoomType.RoomTypeId;
                ReservationLine reservationLine = new ReservationLine(roomType, r.Amount);
                reservationLines.Add(reservationLine);
            }
            Address address = new Address(reservation.Person.Address.StreetName, reservation.Person.Address.HouseNo, reservation.Person.Address.Floor, reservation.Person.Address.ZipCode, reservation.Person.Address.Country);
            
            Person person = new Person(reservation.Person.FirstName, reservation.Person.LastName,reservation.Person.Email,reservation.Person.PhoneNo,address);
            Reservation convertingReservation = new Reservation();
            convertingReservation.ReservationStartDate = reservation.ReservationStartDate;
            convertingReservation.ReservationEndDate = reservation.ReservationEndDate;
            convertingReservation.ReservationLines = reservationLines;
            convertingReservation.person = person;
            convertingReservation.IsPaid = reservation.IsPaid;
            convertingReservation.PayAtLocation = reservation.PayAtLocation;
            convertingReservation.ReservationNote = reservation.ReservationNote;
         
            return convertingReservation;
             
        }
    }
}
