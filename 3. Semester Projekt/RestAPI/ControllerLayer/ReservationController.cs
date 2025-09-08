using Microsoft.AspNetCore.Mvc;
using RestAPI.BusinesslogicLayer;
using RestAPI.DTOs;

[Route("api/[controller]")] // <--- THIS sets the route: /api/calender
[ApiController] // <--- THIS IS VERY IMPORTANT



public class ReservationController : ControllerBase
{
    IReservationLogic _reservationLogic;
    IDateSearchLogic _dateSearch;

    public ReservationController(IReservationLogic reservationLogic, IDateSearchLogic dateSearch)
    {
        _reservationLogic = reservationLogic;
        _dateSearch = dateSearch;
    }

    [HttpPost("PostReservation")]
    public ActionResult<bool> postReservation(ReservationDTO reservationDTO)
    {
        ActionResult<bool> foundReturn;
        bool result;
        if (reservationDTO != null)
        {
            result = _reservationLogic.PostReservation(reservationDTO);

            if (result == true)
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
        //Rammer ikke fejlkode nogensinde
        foundReturn = new StatusCodeResult(500);
        return foundReturn;
    }

    //Post fordi den tager imod en liste, som er en compleks datatype
    [HttpPost("check")]
    public ActionResult<ViewModelReservationDTO> Chekavailability([FromBody] List<DateTime> datadates)
    {
        ActionResult<ViewModelReservationDTO> foundReturn;        
        ViewModelReservationDTO Reservation = new();
        if (datadates != null)
        {
            Reservation = _dateSearch.Chekavailability(datadates);
        }
        // Evaluate
        if (Reservation != null)
        {           
            foundReturn = Ok(Reservation);
        }

        else
        {
            foundReturn = new StatusCodeResult(500);    // Internal server error
        }
        return foundReturn;
    }
}
