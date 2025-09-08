using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataAccess.ModelLayer;

namespace DataAccess.DatabaseLayer
{
    public interface IRoomAccess
    {
        int GetAvailableRoom(int roomId);

        bool CreateRoom(Room room);
        bool DeleteRoom(String roomNo);
        bool UpdateRoom(Room room);
        Room GetRoom(String roomNo);

    }
}
