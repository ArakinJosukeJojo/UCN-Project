using System.Runtime.CompilerServices;
using DataAccess.ModelLayer;
using Microsoft.Identity.Client;
using RestAPI.DTOs;
namespace RestAPI.ModelConversion
{
    public class RoomtypeDTOConvert
    {

        public RoomTypeDTO ToRoomtypeDTO(RoomType rt, int availability) {
            RoomTypeDTO rtDTO = new RoomTypeDTO (rt.RoomTypeId, rt.RoomTypeName, rt.Description, rt.BedSpace, rt.Price, availability);

            return rtDTO;
        }
        public SmallRoomTypeDTO ToSmallRoomTypeDTO(RoomType rt)
        {
            SmallRoomTypeDTO s = new SmallRoomTypeDTO(rt.RoomTypeId, rt.Description, rt.BedSpace, rt.RoomTypeName, rt.Price);
            return s;
        }

        public TypeQuantityDTO ToTypeQuantityDTO(RoomType rt, int availability, DateTime startDate, DateTime endDate)
        {
            TypeQuantityDTO t = new TypeQuantityDTO(rt.RoomTypeId, rt.RoomTypeName, rt.Description, rt.BedSpace, rt.Price,
    availability, startDate, endDate, 0, false, false);

            return t;
        }


        public static List<TypeQuantityDTO?>? FromRoomTypeCollection(List<RoomType> roomTypes)
        {
            var dtoList = new List<TypeQuantityDTO>();
            foreach (var roomType in roomTypes)
            {
                if (roomType != null)
                {
                    var dto = new TypeQuantityDTO(
                        roomType.RoomTypeId,
                        roomType.RoomTypeName,
                        roomType.Description,
                        roomType.BedSpace,
                        roomType.Price,
                        0, // default availability
                        DateTime.Today,
                        DateTime.Today.AddDays(1), // dummy, but reasonable default
                        roomType.ReservationNo ?? 0,
                        roomType.CheckedIn,
                        roomType.CheckedOut
                    );
                    dtoList.Add(dto);
                }
            }
            return dtoList;
        }

        public static List<TypeQuantityDTO?>? FromRoomTypeCollectionByDate(List<RoomType> roomTypes, DateTime startDate, DateTime endDate)
        {
            var dtoList = new List<TypeQuantityDTO>();
            foreach (var roomType in roomTypes)
            {
                if (roomType != null)
                {
                    var guestName = $"{roomType.Guest?.FirstName} {roomType.Guest?.LastName}".Trim();
                    var dto = new TypeQuantityDTO(
                        roomType.RoomTypeId,
                        roomType.RoomTypeName,
                        roomType.Description,
                        roomType.BedSpace,
                        roomType.Price,
                        0,
                        roomType.ReservationStartDate ?? DateTime.MinValue,  // Use actual reservation date
                        roomType.ReservationEndDate ?? DateTime.MinValue,
                        roomType.ReservationNo ?? 0,
                        roomType.CheckedIn,
                        roomType.CheckedOut,
                        guestName,
                        roomType.Guest?.PhoneNumber

                    );
                    dtoList.Add(dto);
                }
            }
            return dtoList;
        }

        public static List<TypeQuantityDTO?>? FromRoomTypeCollectionByName(List<RoomType> names)
        {
            var dtoList = new List<TypeQuantityDTO>();
            foreach (var roomType in names)
            {
                if (roomType != null)
                {
                    var guestName = $"{roomType.Guest?.FirstName} {roomType.Guest?.LastName}".Trim();
                    var dto = new TypeQuantityDTO(
                        roomType.RoomTypeId,
                        roomType.RoomTypeName,
                        roomType.Description,
                        roomType.BedSpace,
                        roomType.Price,
                        0,
                        roomType.ReservationStartDate ?? DateTime.MinValue,  // Use actual reservation date
                        roomType.ReservationEndDate ?? DateTime.MinValue,
                        roomType.ReservationNo ?? 0,
                        roomType.CheckedIn,
                        roomType.CheckedOut,
                        guestName,
                        roomType.Guest?.PhoneNumber
                    );
                    dtoList.Add(dto);
                }
            }
            return dtoList;
        }

        public static TypeQuantityDTO? FromRoomtype(RoomType inRoomType)
        {
            if (inRoomType != null)
            {
                var converter = new RoomtypeDTOConvert();
                int dummyAvailability = 0;
                DateTime dummyStart = DateTime.MinValue;
                DateTime dummyEnd = DateTime.MinValue;
                return converter.ToTypeQuantityDTO(inRoomType, dummyAvailability, dummyStart, dummyEnd);

            }
            return null;

        }

    }
}

