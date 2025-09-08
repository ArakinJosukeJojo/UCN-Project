using RestAPI.DTOs;
using RestAPI.ModelConversion;
using DataAccess.DatabaseLayer;
using DataAccess.ModelLayer;

namespace RestAPI.BusinesslogicLayer
{
    public class DateSearchLogic : IDateSearchLogic
    {
        private readonly IRoomtypeAccess? _roomtypeAccess;
        private readonly IRoomAccess? _roomAccess;

        public DateSearchLogic(IRoomtypeAccess roomtypeAccess, IRoomAccess roomAccess)
        {
            _roomtypeAccess = roomtypeAccess;
            _roomAccess = roomAccess;
        }


        //Slet?
        public CalenderDTO? Get(DateTime? startDate, DateTime? endDate)
        {
            try
            {
                return DateDTOConvert.ToDate(startDate, endDate);
            }
            catch
            {
                return null;
            }
        }

        public ViewModelReservationDTO? Chekavailability(List<DateTime> datadates)
        {
            RoomTypeDTO workingRoomTypeDTO;
            ViewModelReservationDTO Reservation;
            int workingAmount;
            RoomtypeDTOConvert roomtypeDTOConvert = new RoomtypeDTOConvert();
            List<RoomType> types = _roomtypeAccess.GetAllRoomTypes();
            List<RoomTypeDTO> RoomTypes = new List<RoomTypeDTO>();

            foreach (RoomType r in types)
            {
                workingAmount = _roomtypeAccess.GetRoomTypeavailability(r, datadates);
                workingAmount = _roomAccess.GetAvailableRoom(r.RoomTypeId) - workingAmount; //Den nye metode
                workingRoomTypeDTO = roomtypeDTOConvert.ToRoomtypeDTO(r, workingAmount);
                RoomTypes.Add(workingRoomTypeDTO);
            }
            if(RoomTypes != null)
            {
                Reservation = new ViewModelReservationDTO(RoomTypes, datadates[0], datadates[1]);
            }
            else
            {
                Reservation = new ViewModelReservationDTO();
                Reservation.Reservation.ReservationStartDate = datadates[0];
                Reservation.Reservation.ReservationEndDate = datadates[1];
            }

            return Reservation;
        }

        public IEnumerable<TypeQuantityDTO> GetReservation()
        {
            List<TypeQuantityDTO?>? foundDTOS;
            try
            {
                List<RoomType>? foundReservation = _roomtypeAccess.GetAllRoomTypes();
                Console.WriteLine($"Found {foundReservation?.Count} room types");
                foundDTOS = RoomtypeDTOConvert.FromRoomTypeCollection(foundReservation);
                Console.WriteLine($"Found {foundDTOS?.Count} room types");

            }
            catch
            {
                foundDTOS = null;
            }
            return foundDTOS;

        }

        public IEnumerable<TypeQuantityDTO> GetReservationBySearch(List<DateTime> searchDates)
        {
            List<TypeQuantityDTO?>? foundDTOS;
            try
            {
                List<RoomType>? foundReservation = _roomtypeAccess.GetRoomTypesByDate(searchDates);
                Console.WriteLine($"Found {foundReservation?.Count} room types");
                foundDTOS = RoomtypeDTOConvert.FromRoomTypeCollectionByDate(foundReservation, searchDates[0], searchDates[1]);
                Console.WriteLine($"Found {foundDTOS?.Count} room types");

            }
            catch
            {
                foundDTOS = null;
            }
            return foundDTOS;

        }

        public IEnumerable<TypeQuantityDTO> GetReservationByName(List<string> searchName)
        {
            List<TypeQuantityDTO?>? foundDTOS;
            try
            {
                List<RoomType>? foundReservation = _roomtypeAccess.GetRoomTypesByName(searchName);
                Console.WriteLine($"Found {foundReservation?.Count} room types");
                foundDTOS = RoomtypeDTOConvert.FromRoomTypeCollectionByName(foundReservation);
                Console.WriteLine($"Found {foundDTOS?.Count} room types");

            }
            catch
            {
                foundDTOS = null;
            }
            return foundDTOS;

        }
    }
}
