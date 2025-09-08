using RestAPI.DTOs;

namespace RestAPI.BusinesslogicLayer
{
    public interface IDateSearchLogic
    {
        CalenderDTO? Get(DateTime? startDate, DateTime? endDate);
        ViewModelReservationDTO? Chekavailability(List<DateTime> datadates);
        IEnumerable<TypeQuantityDTO> GetReservation();
        IEnumerable<TypeQuantityDTO> GetReservationBySearch(List<DateTime> searchDates);
        IEnumerable<TypeQuantityDTO> GetReservationByName(List<string> searchName);
    }
}
