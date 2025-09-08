namespace RestAPI.DTOs
{
    public class CalenderDTO
    {
         public CalenderDTO()
        { 
        }
        public CalenderDTO(DateTime? startDate, DateTime? endDate)
        {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public DateTime? startDate { get; set; }
        public DateTime? endDate { get; set; }
    }
   
}
