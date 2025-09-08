using System;
using System;
using Moq;
using Xunit;
using Microsoft.Data.SqlClient;
using DataAccess.DatabaseLayer;
using System.Data;
using DataAccess.ModelLayer;
using Xunit.Abstractions;
using Microsoft.VisualStudio.TestPlatform.Utilities;
using Microsoft.Extensions.Configuration;
using Test;

namespace TransactionTest
{
    public class TransactionTest : IClassFixture<SqlScriptFixture>
    {
        private readonly SqlScriptFixture _fixture;
        private readonly ITestOutputHelper _output;
        
        public TransactionTest(ITestOutputHelper output, SqlScriptFixture fixture)
        {
            
            _output = output;
            _fixture = fixture;
        }

        [Fact]
        public void Sucesesfull_TransactionTest()
        {

            // Arrange
            String _connectionString = _fixture.GetConnectionString();
            RoomType roomType = new RoomType(4, "Family", "Family room", 5, 30);
            ReservationLine reservationLine = new ReservationLine(roomType, 1);
            List<ReservationLine> reservationLines = new List<ReservationLine>();
            reservationLines.Add(reservationLine);
            Address address = new Address("bobway", 808, "3th", 808808, "Monaco");
            Person person = new Person("bob", "bobesen", "bobMail@bob.bob", "80880800", address);
            Reservation reservation = new Reservation();
            reservation.ReservationStartDate = DateTime.Now;
            reservation.ReservationEndDate = DateTime.Now.AddDays(7);
            reservation.ReservationLines = reservationLines;
            reservation.person = person;
            reservation.IsPaid = true;
            reservation.PayAtLocation = false;
            reservation.ReservationNote = "dette er en Test";
            ReservationAccess reservationAccess = new ReservationAccess(_connectionString);

            // Act

            bool result = reservationAccess.Transaction(reservation);

            // Assert

            Assert.True(result);
        }

        [Fact]
        public void Failled_TransactionTest()
        {
            // Arrange
            String _connectionString = _fixture.GetConnectionString();
            RoomType roomType = new RoomType(4, "Family", "Family room", 5, 30);
            ReservationLine reservationLine = new ReservationLine(roomType, 1000);
            List<ReservationLine> reservationLines = new List<ReservationLine>();
            reservationLines.Add(reservationLine);
            Address address = new Address("bobway", 808, "3th", 808808, "Monaco");
            Person person = new Person("bob", "bobesen", "bobMail@bob.bob", "80880800", address);
            Reservation reservation = new Reservation();
            reservation.ReservationStartDate = DateTime.Now;
            reservation.ReservationEndDate = DateTime.Now.AddDays(7);
            reservation.ReservationLines = reservationLines;
            reservation.person = person;
            reservation.IsPaid = true;
            reservation.PayAtLocation = false;
            reservation.ReservationNote = "dette er en Test";
            ReservationAccess reservationAccess = new ReservationAccess(_connectionString);
            
            // Act

            bool result = reservationAccess.Transaction(reservation);

            // Assert

            Assert.False(result);
        }
    }
}
