using System;
using RestAPI.DTOs;

namespace RestAPI.ModelConversion
{
    public class DateDTOConvert
    {
       

        // Convert from CalenderDTO object to Calender object
        public static CalenderDTO ToDate(DateTime? startDate, DateTime? endDate)
        {
            return new CalenderDTO
            {
                startDate = startDate,
                endDate = endDate
            };
        }
    }
}
