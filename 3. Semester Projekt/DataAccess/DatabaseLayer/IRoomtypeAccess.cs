using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataAccess.ModelLayer;
using Microsoft.Data.SqlClient;

namespace DataAccess.DatabaseLayer
{
    public interface IRoomtypeAccess
    {
        public List<RoomType> GetAllRoomTypes();
      
        public int GetRoomTypeavailability(RoomType roomType, List<DateTime> dates);

        List<RoomType> GetRoomTypesByDate(List<DateTime> searchDate);

        List<RoomType> GetRoomTypesByName(List<string> searchName);
    }
}
