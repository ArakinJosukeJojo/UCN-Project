namespace Hotel_Web.Models
{
    public class Address
    {
        public string StreetName { get; set; }
        public int HouseNo { get; set; }
        public string Floor {  get; set; }
        public int ZipCode {  get; set; }
        public string Country {  get; set; }


        public Address() { }
        public Address(string streetName, int houseNo, string floor, int zipCode, string country)
        {
            StreetName = streetName;
            HouseNo = houseNo;
            Floor = floor;
            ZipCode = zipCode;
            Country = country;
        }
    }
}
