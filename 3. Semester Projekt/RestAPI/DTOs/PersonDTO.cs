namespace RestAPI.DTOs
{
    public class PersonDTO
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public string PhoneNo { get; set; }
        public AddressDTO Address { get; set; }

        public PersonDTO() { }
        public PersonDTO(string firstName, string lastName, string email, string phoneNo, AddressDTO address)
        {
            FirstName = firstName;
            LastName = lastName;
            Email = email;
            PhoneNo = phoneNo;
            Address = address;
        }

    }
}
