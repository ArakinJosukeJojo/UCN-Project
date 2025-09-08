using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestAPI.DTOs;

namespace DTOTests
{
    public class ReservationDTOTests
    {
        [Fact]
        public void ReservationDTO_Constructor_SetsPropertiesCorrectly()
        {
            // Arrange
            DateTime startDate = new DateTime(2025, 05, 20);
            DateTime endDate = new DateTime(2025, 05, 25);
            bool payAtLocation = true;
            bool isPaid = false;
            string note = "Test reservation note";

            var reservationLines = new List<ReservationLineDTO>
            {
                new ReservationLineDTO(roomType: new RoomTypeDTO(1), amount: 2)
            };

            string firstName = "Test";
            string lastName = "User";
            string email = "test@example.com";
            string phoneNo = "1234567";
            string street = "Test Street";
            int houseNo = 1;
            string floor = "2A";
            int zipCode = 12345;
            string country = "USA";
            AddressDTO address = new AddressDTO(street, houseNo, floor , zipCode , country);
            PersonDTO person = new PersonDTO(firstName, lastName, email, phoneNo, address);

            // Act
            var dto = new ReservationDTO(
                startDate, endDate, payAtLocation, isPaid, note,
                reservationLines, person
            );

            // Assert
            Assert.Equal(startDate, dto.ReservationStartDate);
            Assert.Equal(endDate, dto.ReservationEndDate);
            Assert.True(dto.PayAtLocation);
            Assert.False(dto.IsPaid);
            Assert.Equal(note, dto.ReservationNote);

            Assert.Single(dto.ReservationLines);
            Assert.Equal(1, dto.ReservationLines[0].RoomType.RoomTypeId);
            Assert.Equal(2, dto.ReservationLines[0].Amount);

            Assert.Equal(firstName, dto.Person.FirstName);
            Assert.Equal(lastName, dto.Person.LastName);
            Assert.Equal(email, dto.Person.Email);
            Assert.Equal(phoneNo, dto.Person.PhoneNo);
            Assert.Equal(street, dto.Person.Address.StreetName);
            Assert.Equal(houseNo, dto.Person.Address.HouseNo);
            Assert.Equal(floor, dto.Person.Address.Floor);
            Assert.Equal(zipCode, dto.Person.Address.ZipCode);
            Assert.Equal(country, dto.Person.Address.Country);
        }
    }
}
