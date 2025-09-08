
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.ModelLayer
{
    public class Reservation
    {
        public Reservation() { }

        // 1️⃣ Core fields
        public Reservation(DateTime reservationStartDate, DateTime reservationEndDate, bool checkedIn, bool checkedOut,
                          bool payAtLocation, bool isConfirmationSent, bool isPaid, string reservationNote)
        {
            ReservationStartDate = reservationStartDate;
            ReservationEndDate = reservationEndDate;
            CheckedIn = checkedIn;
            CheckedOut = checkedOut;
            PayAtLocation = payAtLocation;
            IsConfirmationSent = isConfirmationSent;
            IsPaid = isPaid;
            ReservationNote = reservationNote;
        }

        // 2️⃣ Core + PhoneNo
     

        // 3️⃣ Core + Person + ReservationLines (NO PhoneNo)
        public Reservation(DateTime reservationStartDate, DateTime reservationEndDate, bool checkedIn, bool checkedOut, bool payAtLocation, bool isConfirmationSent, bool isPaid, string reservationNote, Person reservationPerson, List<ReservationLine> listOfReservationLines) : this(reservationStartDate, reservationEndDate, checkedIn, checkedOut, payAtLocation, isConfirmationSent, isPaid, reservationNote)
        {
            person = reservationPerson;
            ReservationLines = listOfReservationLines;
        }




        public DateTime ReservationStartDate { get; set; }
        public DateTime ReservationEndDate { get; set; }
        public bool? CheckedIn {  get; set; }
        public bool? CheckedOut { get; set; }
        public bool? PayAtLocation { get; set; }
        public bool? IsPaid { get; set; }
        public bool? IsConfirmationSent { get; set; }
        public string ReservationNote { get; set; }
        public Person person { get; set; } 
        public List<ReservationLine> ReservationLines { get; set; }
    }
}

