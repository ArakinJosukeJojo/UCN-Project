using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DesktopClient.ModelLayer
{
    public class RoomType
    {
        
        public int? AvailableAmount { get; set; }
        public string? Description { get; set; }
        public int? BedSpace { get; set; }
        public string? RoomTypeName { get; set; }
        //public string Code { get; set; }
        public Decimal? Price { get; set; }
        public int? RoomTypeId { get; set; }

        public RoomType() { }

        public RoomType(int roomTypeId,int availableAmount, string description, int bedSpace, string RoomTypeName, decimal price)
        {
            RoomTypeId = RoomTypeId;
            AvailableAmount = availableAmount;
            Description = description;
            BedSpace = bedSpace;
            RoomTypeName = RoomTypeName;
            Price = price;
        }
        public RoomType(int roomTypeId, string RoomTypeName)
        {
            RoomTypeId = RoomTypeId;
            RoomTypeName = RoomTypeName;
        }
    }
}
