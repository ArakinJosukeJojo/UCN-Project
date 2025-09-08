using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataAccess.ModelLayer;

namespace DataAccess.DatabaseLayer
{
    public interface IBookingAccess
    {
        List<string>? CheckIn(int checkIn);
        List<string>? CheckOut(int resavationNO);

        bool AflysReservation(int reservationsNo);
    }
}
