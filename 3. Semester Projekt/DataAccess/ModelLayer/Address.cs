using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.ModelLayer
{
    public class Address
    {
        public string StreetName { get; set; }
        public int HouseNumber { get; set; }
        public string Floor { get; set; }
        public int ZipCode { get; set; }
        public string Country { get; set; }


        public Address() { }
        public Address(string streetName, int houseNo, string floor, int zipCode, string country)
        {
            StreetName = streetName;
            HouseNumber = houseNo;
            Floor = floor;
            ZipCode = zipCode;
            Country = country;
        }
    }
}

