using DataAccess.ModelLayer;
using RestAPI.ModelConversion;
namespace RestAPI.DTOs

{
    public class TypeQuantityDTO
    {
        public TypeQuantityDTO(int roomTypeId, string roomTypeName, string description, int bedSpace, decimal price,
    int quantity, DateTime startDate, DateTime endDate, int reservationNo, bool checkedIn, bool checkedOut,
    string? guestFullName = null, string? guestPhoneNo = null)
        {
            RoomTypeId = roomTypeId;
            RoomTypeName = roomTypeName;
            Description = description;
            BedSpace = bedSpace;
            Price = price;
            Quantity = quantity;
            StartDate = startDate;
            EndDate = endDate;
            GuestFullName = guestFullName;
            GuestPhoneNo = guestPhoneNo;
            ReservationNo = reservationNo;
            CheckedIn = checkedIn;
            CheckedOut = checkedOut;
            
        }


        public int RoomTypeId { get; set; }

        public string RoomTypeName { get; set; }

        public string Description { get; set; }

        public int BedSpace { get; set; }
        public Decimal Price { get; set; }
        public int Availability { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }

        public int Quantity { get; set; }
        public string? GuestFullName { get; set; }
        public string? GuestPhoneNo { get; set; }

        public int? ReservationNo { get; set; }
    
        public bool CheckedIn {  get; set; }
        public bool CheckedOut { get; set; }

        
    public override string ToString() //ToString sørger for at de dukker op listen.
        {
            return $"Guest: {GuestFullName}, Roomtype: {RoomTypeName}, Start: {StartDate:d}, End: {EndDate:d}";
        }
    }

}
