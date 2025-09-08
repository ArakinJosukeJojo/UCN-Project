using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using DataAccess.ModelLayer;
namespace DesktopClient.ModelLayer
{
    public class TypeQuantity
    {
        public int RoomTypeId { get; set; }
        public string RoomTypeName { get; set; }
        public string Description { get; set; }
        public int BedSpace { get; set; }
        public decimal Price { get; set; }
        public int Availability { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public int Quantity { get; set; }
        public string? GuestFullName { get; set; }
        public int? GuestPhoneNo { get; set; }
        public int? ReservationNo { get; set; }
        public bool CheckedIn { get; set; }
        public bool CheckedOut { get; set; }

        public override string ToString()
        {
            return $"Guest: {GuestFullName}, Roomtype: {RoomTypeName}, Start: {StartDate:d}, End: {EndDate:d}";
        }
    }
}
