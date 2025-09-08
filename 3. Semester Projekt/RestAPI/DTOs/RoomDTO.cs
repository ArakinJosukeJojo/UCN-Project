namespace RestAPI.DTOs
{
    public class RoomDTO
    {
        public RoomDTO() { }
        public RoomDTO(string roomNo, string roomInfo)
        {
            RoomNo = roomNo;
            RoomInfo = roomInfo;
        }
        public RoomDTO(string roomNo, string roomInfo, int RoomTypeId) : this(roomNo, roomInfo)
        {
            RoomNo = roomNo;
            RoomInfo = roomInfo;
            roomTypeId = RoomTypeId;
        }

        public RoomDTO(string roomNo, string roomInfo, RoomTypeDTO roomTypeDTO) : this(roomNo, roomInfo) 
        {
            RoomNo = roomNo;
            RoomInfo = roomInfo;
            RoomTypeDTO = roomTypeDTO;
            roomTypeId = RoomTypeDTO.RoomTypeId;            
        }

        public String RoomNo { get; set; }
        public string RoomInfo { get; set; }
        public int roomTypeId { get; set; }
        public string? RoomTypeName { get; set; }
        public string? RoomTypeDescription { get; set; }
        public bool? IsOccupied { get; set; }
        public int? ReservationNo { get; set; } // Nullable to allow for rooms that are not reserved
        public RoomTypeDTO? RoomTypeDTO { get; set; }
    }
}
