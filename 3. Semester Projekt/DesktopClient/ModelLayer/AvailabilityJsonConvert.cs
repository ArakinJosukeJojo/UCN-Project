using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DesktopClient.ServiceLayer;

public class AvailabilityJsonConvert
{

    public AvailabilityJsonConvert(int roomTypeId, string roomTypeName, string description, int bedSpace, Decimal price, int availability, DateTime startDate, DateTime endDate)
    {
        RoomTypeId = roomTypeId;
        RoomTypeName = roomTypeName;
        Description = description;
        BedSpace = bedSpace;
        Price = price;
        Availability = availability;
        StartDate = startDate;
        EndDate = endDate;
    }

    public int RoomTypeId { get; set; }

    public string RoomTypeName { get; set; }

    public string Description { get; set; }

    public int BedSpace { get; set; }
    public Decimal Price { get; set; }
    public int Availability { get; set; }
    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
}