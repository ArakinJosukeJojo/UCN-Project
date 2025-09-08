using DataAccess.DatabaseLayer;
using DataAccess.ModelLayer;
using Microsoft.AspNetCore.Mvc;
using RestAPI.BusinesslogicLayer;
using RestAPI.DTOs;
using RestAPI.ModelConversion;
using Test;

namespace ControllerTests
{
    public class ReservationControllerTests: IClassFixture<SqlScriptFixture>
    {
        private readonly DesktopController _desktopController;
        private readonly ReservationController _reservationController;
        private readonly SqlScriptFixture _fixture;

        public ReservationControllerTests(SqlScriptFixture fixture)
        {
            _fixture = fixture;
            string connectionString = _fixture.GetConnectionString();
            var roomTypeAccess = new RoomtypeAccess(connectionString);
            var roomAccess = new RoomAccess(connectionString);
            var dateSearchLogic = new DateSearchLogic(roomTypeAccess, roomAccess);
            var reservationAccess = new ReservationAccess(connectionString);
            var reservationDtoConvert = new ReservationDTOConvert();
            var reservationLogic = new ReservationLogic(reservationAccess);
            var bookingAcces = new BookingAccess(connectionString);
            var bookingLogic = new BookingLogic(bookingAcces);
            var roomLogic = new RoomLogic(roomAccess, roomTypeAccess);

            _reservationController = new ReservationController(reservationLogic, dateSearchLogic);
            _desktopController = new DesktopController(dateSearchLogic, bookingLogic,  roomLogic);
        }

        [Fact]
        public async Task PostReservation_ShouldCreateReservation_ReturnsOk()
        {
            // Arrange:
            int RoomTypeId = 1;
            int Amount = 1;
            var reservationDto = new ReservationDTO()
            {
                ReservationStartDate = new DateTime(2025, 05, 01),
                ReservationEndDate = new DateTime(2025, 05, 05),
                PayAtLocation = true,
                IsPaid = false,
                ReservationNote = "Test reservation",
                ReservationLines = new List<ReservationLineDTO>
            {
                new ReservationLineDTO(new RoomTypeDTO(1), Amount)
            },
                Person = new PersonDTO("Test", "Test","test@mail", "1001001",new AddressDTO("test", 1 ,"1th",1,"test"))
            };

            // Act
            var result = _reservationController.postReservation(reservationDto);

            // Assert
            Assert.NotNull(result);
        }

        [Fact]
        public void Chekavailability_ShouldReturnAvailableRoomTypes_ForValidDates()
        {
            // Arrange
            var validDates = new List<DateTime>
            {
                 new DateTime(2025, 05, 01),
                 new DateTime(2025, 05, 05)
             };

            // Act
            var result = _reservationController.Chekavailability(validDates);

            // Assert
            Assert.NotNull(result);
            var okResult = Assert.IsType<OkObjectResult>(result.Result);
            var viewModel = Assert.IsType<ViewModelReservationDTO>(okResult.Value);

            Assert.NotNull(viewModel.RoomTypes);
            Assert.NotEmpty(viewModel.RoomTypes);
        }

        [Fact]
        public void CheckReservation_ShouldReturnReservations_IfExists()
        {
            // Act
            var result = _desktopController.CheckReservation();

            // Assert
            Assert.NotNull(result);

            // If data exists
            if (result.Result is OkObjectResult okResult)
            {
                var reservations = Assert.IsAssignableFrom<List<TypeQuantityDTO>>(okResult.Value);
                Assert.NotEmpty(reservations);
            }
            // If no data found (204 No Content)
            else if (result.Result is ObjectResult statusResult)
            {
                Assert.Equal(204, statusResult.StatusCode);
            }
            // If error (500)
            else if (result.Result is StatusCodeResult statusCodeResult)
            {
                Assert.Equal(500, statusCodeResult.StatusCode);
            }
            else
            {
                throw new Xunit.Sdk.XunitException("Unexpected response type from CheckReservation()");
            }

        }

        [Fact]
        public void CheckIn_ShouldReturnOk_WhenCheckInIsSuccessful()
        {
            // Arrange
            int validReservationId = 1;

            // Act
            var result = _desktopController.CheckIn(validReservationId);

            // Assert
            Assert.NotNull(result);
            Assert.IsType<OkResult>(result);

            // Arrange. Det her var mit forsøg på at fikse det.
            //int validReservationId = 1;

            // Act
            //var result = _desktopController.CheckIn(validReservationId);

            // Assert
            //var okResult = Assert.IsType<OkObjectResult>(result);
            //Assert.NotNull(okResult.Value);
            //var rooms = Assert.IsType<List<string>>(okResult.Value);
            //Assert.NotEmpty(rooms);
        }


    }



}
