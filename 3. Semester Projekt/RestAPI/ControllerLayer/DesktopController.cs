using Microsoft.AspNetCore.Mvc;
using RestAPI.BusinesslogicLayer;
using RestAPI.DTOs;

[Route("api/[controller]")] // <--- THIS sets the route: /api/calender
[ApiController] // <--- THIS IS VERY IMPORTANT



public class DesktopController : Controller
{

    private readonly IDateSearchLogic _dateSearch;

    private readonly IRoomLogic _roomLogic;
    private readonly IRoomTypeLogic _roomTypeLogic;


    private readonly IBookingLogic _bookingLogic;
	


    public DesktopController(IDateSearchLogic dateSearch, IBookingLogic bookingLogic, IRoomLogic roomLogic)
    {
        _dateSearch = dateSearch;

        _roomLogic = roomLogic;
        IConfiguration Icon = new ConfigurationBuilder()
            .SetBasePath(AppContext.BaseDirectory)
                .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
                .Build();;
        _roomTypeLogic = new RoomTypeLogic(Icon) ;

        _bookingLogic = bookingLogic;
	

    }

    [HttpGet("GetReservation")]
    public ActionResult<List<TypeQuantityDTO>> CheckReservation()
    {
        ActionResult<List<TypeQuantityDTO>> foundReturn;
        List<TypeQuantityDTO?>? foundReservations = _dateSearch.GetReservation().ToList();

        if (foundReservations != null)
        {
            if (foundReservations.Count > 0)
            {
                Console.WriteLine("We found reservation");
                foundReturn = Ok(foundReservations);
            }
            else
            {
                foundReturn = StatusCode(204);
            }
        }
        else
        {
            foundReturn = new StatusCodeResult(500);
        }
        Console.WriteLine("About to send found reservation.");

        return foundReturn;
    }

    [HttpPost("GetReservationByDate")]
    public ActionResult<List<TypeQuantityDTO>> CheckReservation2([FromBody] List<DateTime> dates)
    {
        List<DateTime> searchDate = dates;
        ActionResult<List<TypeQuantityDTO>> foundReturn;
        List<TypeQuantityDTO?>? foundReservations = _dateSearch.GetReservationBySearch(searchDate).ToList();

        if (foundReservations != null)
        {
            if (foundReservations.Count > 0)
            {
                Console.WriteLine("We found reservation by date");
                foundReturn = Ok(foundReservations);
            }
            else
            {
                foundReturn = StatusCode(204);
            }
        }
        else
        {
            foundReturn = new StatusCodeResult(500);
        }
        Console.WriteLine("About to send found reservation by date.");

        return foundReturn;
    }


    [HttpPost("GetReservationByName")]
    public ActionResult<List<TypeQuantityDTO>> CheckReservation3([FromBody] List<string> names)
    {
        List<string> searchNames = names;
        ActionResult<List<TypeQuantityDTO>> foundReturn;
        List<TypeQuantityDTO?>? foundReservations = _dateSearch.GetReservationByName(searchNames).ToList();

        if (foundReservations != null)
        {
            if (foundReservations.Count > 0)
            {
                Console.WriteLine("We found reservation by date");
                foundReturn = Ok(foundReservations);
            }
            else
            {
                foundReturn = StatusCode(204);
            }
        }
        else
        {
            foundReturn = new StatusCodeResult(500);
        }
        Console.WriteLine("About to send found reservation by date.");

        return foundReturn;
    }

    [HttpPost("CheckIn")]
    public IActionResult CheckIn([FromBody] int checkIn)
    {
        List<string>? assignedRooms = _bookingLogic.CheckIn(checkIn);

        if (assignedRooms != null && assignedRooms.Count > 0)
        {
            return Ok(assignedRooms); // returns JSON array like [101, 102]
        }
        else
        {
            return StatusCode(500, "Failed to check in guest or not enough rooms available.");
        }
    }

    [HttpGet("GetRoomByNo/{roomNo}")]
    public ActionResult<RoomDTO> GetRoomByRoomNo(string roomNo)
    {
        try
        {
           
            RoomDTO? foundRoom = _roomLogic.GetRoomByRoomNo(roomNo);

            if (foundRoom != null)
            {
                return Ok(foundRoom);
            }
            else
            {
                return NotFound($"Room number {roomNo} not found.");
            }
        }
        catch (FormatException)
        {
            return BadRequest("Invalid room number format.");
        }
        catch (Exception)
        {
            return StatusCode(500, "An unexpected error occurred.");
        }
    }

    [HttpDelete("DeleteRoomByRoomNo/{roomNo}")]
    public ActionResult<bool> DeleteRoomByRoomNo(string roomNo)
    {
        ActionResult<bool> foundReturn;
        bool result;
        if (roomNo != null)
        {
            result = _roomLogic.DeleteRoomByRoomNo(roomNo);
            if (result)
            {
                foundReturn = new StatusCodeResult(200);
                return foundReturn;
            }
            else
            {
                foundReturn = new StatusCodeResult(502);
                return foundReturn;
            }
        }
        foundReturn = new StatusCodeResult(500);
        return foundReturn;
    }

    [HttpPost("CreateRoom")]
    public ActionResult<bool> CreateRoom([FromBody] RoomDTO roomDTO)
    {
        ActionResult<bool> foundReturn;
        bool result;
        if (roomDTO != null)
        {
            result = _roomLogic.CreateRoom(roomDTO);
            if (result)
            {
                foundReturn = new StatusCodeResult(200);
                return foundReturn;
            }
            else
            {
                foundReturn = new StatusCodeResult(502);
                return foundReturn;
            }
        }
        foundReturn = new StatusCodeResult(500);
        return foundReturn;
    }

    [HttpPut("UpdateRoom")]
    public ActionResult<bool> UpdateRoom([FromBody] RoomDTO roomDTO)
    {
        ActionResult<bool> foundReturn;
        bool result;
        if (roomDTO != null)
        {
            result = _roomLogic.UpdateRoom(roomDTO);
            if (result)
            {
                foundReturn = new StatusCodeResult(200);
                return foundReturn;
            }
            else
            {
                foundReturn = new StatusCodeResult(502);
                return foundReturn;
            }
        }
        foundReturn = new StatusCodeResult(500);
        return foundReturn;

    }

    [HttpPost("CheckOut")]
    //Ændrer returtype
    public ActionResult<List<TypeQuantityDTO>> CheckOut([FromBody] int checkOut)
    {
        // samme som check in

        List<string>? checkedOutRooms = _bookingLogic.CheckOut(checkOut);

        if (checkedOutRooms != null && checkedOutRooms.Count > 0)
        {
            return Ok(checkedOutRooms); // returns JSON array like [101, 102]
        }
        else
        {
            return StatusCode(500, "Failed to check in guest or not enough rooms available.");
        }
    }


    [HttpDelete("AflysReservation/{reservationNo}")]
    public IActionResult AflysReservation(int reservationNo)
    {
        bool success = _bookingLogic.AflysReservation(reservationNo);
        if (!success)
        {
            return NotFound($"Reservation {reservationNo} was not found.");
        }

        return NoContent();
    }

    [HttpGet("GetAllRoomTypes")]
    public ActionResult<List<SmallRoomTypeDTO>> GetAllRoomTypes()
    {
        ActionResult<List<SmallRoomTypeDTO>> foundReturn;
        List<SmallRoomTypeDTO?>? foundRoomTyps = _roomTypeLogic.GetAllRoomTypesSmall();

        if (foundRoomTyps != null)
        {
            if (foundRoomTyps.Count > 0)
            {
                Console.WriteLine("We found RoomTyps");
                foundReturn = Ok(foundRoomTyps);
            }
            else
            {
                foundReturn = StatusCode(204);
            }
        }
        else
        {
            foundReturn = new StatusCodeResult(500);
        }
        Console.WriteLine("About to send found RoomTyps.");

        return foundReturn;
    }
}
