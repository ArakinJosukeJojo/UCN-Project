namespace Hotel_Web.Models
{
    public class Person
    {
        public string FirstName {  get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public string PhoneNo{ get; set;}
        public Address Address { get; set; }


        public Person() { }
        public Person(string firstName, string lastName, string email, string phoneNo, Address address)
        {
            FirstName = firstName;
            LastName = lastName;
            Email = email;
            PhoneNo = phoneNo;
            Address = address;
        }
    }
}
