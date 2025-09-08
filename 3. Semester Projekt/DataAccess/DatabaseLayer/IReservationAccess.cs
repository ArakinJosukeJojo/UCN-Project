using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataAccess.ModelLayer;

namespace DataAccess.DatabaseLayer
{
    public interface IReservationAccess
    {
        public void CreateReservation();
        public bool Transaction(Reservation reservation);

    }
}
