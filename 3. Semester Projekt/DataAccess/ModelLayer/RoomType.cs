using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.ModelLayer
{
    public class RoomType
    {
        public RoomType() { }

        public RoomType(int roomTypeId)
        {
            RoomTypeId = roomTypeId;
        }

        public RoomType(int roomTypeId, string roomTypeName, string description, int bedSpace, decimal price)
        {
            RoomTypeId = roomTypeId;
            RoomTypeName = roomTypeName;
            Description = description;
            BedSpace = bedSpace;
            Price = price;
        }

        public RoomType(
            int roomTypeId,
            string roomTypeName,
            string description,
            int bedSpace,
            decimal price,
            int reservationNo,
            DateTime reservationStartDate,
            DateTime reservationEndDate,
            Person guest,
            Address guestAddress,
            bool checkedIn = false,
            bool checkedOut = false)
            : this(roomTypeId, roomTypeName, description, bedSpace, price)
        {
            ReservationNo = reservationNo;
            ReservationStartDate = reservationStartDate;
            ReservationEndDate = reservationEndDate;
            Guest = guest;
            GuestAddress = guestAddress;
            CheckedIn = checkedIn;
            CheckedOut = checkedOut;
        }

        public int RoomTypeId { get; set; }
        public string RoomTypeName { get; set; }
        public string Description { get; set; }
        public int BedSpace { get; set; }
        public decimal Price { get; set; }

        public int? ReservationNo { get; set; }
        public DateTime? ReservationStartDate { get; set; }
        public DateTime? ReservationEndDate { get; set; }

        public bool CheckedIn { get; set; }
        public bool CheckedOut { get; set; }

        public Person? Guest { get; set; }
        public Address? GuestAddress { get; set; }
    }
}
