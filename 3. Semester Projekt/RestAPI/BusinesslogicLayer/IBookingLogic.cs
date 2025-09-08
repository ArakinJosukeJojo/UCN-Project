namespace RestAPI.BusinesslogicLayer
{
    public interface IBookingLogic
    {
        List<string>? CheckIn(int checkIn);
        List<string>? CheckOut(int checkOut);
        bool AflysReservation(int reservationsNo);
    }
}
