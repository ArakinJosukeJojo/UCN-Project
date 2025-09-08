using DataAccess.DatabaseLayer;
using RestAPI.BusinesslogicLayer;
using RestAPI.ModelConversion;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddScoped<IDateSearchLogic, DateSearchLogic>();
builder.Services.AddScoped<IRoomtypeAccess, RoomtypeAccess>(); // or whatever your implementation is
builder.Services.AddScoped<IRoomAccess, RoomAccess>();
builder.Services.AddScoped<IReservationAccess, ReservationAccess>();
builder.Services.AddScoped<IReservationLogic, ReservationLogic>();
builder.Services.AddScoped<IRoomLogic, RoomLogic>();
//builder.Services.AddScoped<IRoomTypeLogic, RoomTypeLogic>();
builder.Services.AddScoped<ReservationDTOConvert>();
builder.Services.AddScoped<IBookingLogic, BookingLogic>();
builder.Services.AddScoped<IBookingAccess, BookingAccess>();
builder.Services.AddSingleton<IConfiguration>(builder.Configuration);

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
