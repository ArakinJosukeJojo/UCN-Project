CREATE TABLE PersonTypes(
PersonTypeId int IDENTITY (1,1) PRIMARY KEY,
PersonTypeName nvarchar (50) NOT NULL,
)

CREATE TABLE PersonAddress(
AddressId int IDENTITY (1,1) PRIMARY KEY,
StreetName nvarchar(50) NOT NULL,
HouseNo int NOT NULL,
FloorNo nvarchar(5)  Not Null,
ZipCode int NOT NULL,
Country nvarchar(50) NOT NULL
)

CREATE TABLE Persons(
PhoneNo varchar(100) PRIMARY KEY NOT NULL,
FName nvarchar(50) NOT NULL,
LName nvarchar(50) NOT NULL,
Email nvarchar(50) NOT NULL,
AddressId int NOT NULL,
FOREIGN KEY (AddressId) REFERENCES PersonAddress(AddressId),
PersonTypeId int,
FOREIGN KEY (PersonTypeId) REFERENCES PersonTypes (PersonTypeId),
)

CREATE TABLE Reservations(
ReservationNo int IDENTITY (1,1) PRIMARY KEY,
ReservationStartDate DATETIME NOT NULL,
ReservationEndDate DATETIME NOT NULL,
CheckedIn BIT,
CheckedOut BIT,
PayAtLocation BIT,
IsConfirmationSent BIT,
IsPaid BIT,
ReservationNote nvarchar(300) NOT NULL,
PhoneNo varchar(100) NOT NULL,
FOREIGN KEY (PhoneNo) REFERENCES Persons (PhoneNo),
)

CREATE TABLE RoomTypes(
RoomTypeId INT IDENTITY (1,1) PRIMARY KEY,
RoomTypeName nvarchar (50) NOT NULL,
RoomTypeDescription nvarchar (300) NOT NULL, 
BedSpace int NOT NULL
)

CREATE TABLE Rooms (
RoomNo nvarchar (4) PRIMARY KEY,
RoomInfo VARCHAR(300) NOT NULL,
IsOccupied BIT NOT NULL,
RoomTypeId INT NOT NULL,
ReservationNo INT,
FOREIGN KEY (RoomTypeId) REFERENCES RoomTypes(RoomTypeId),
FOREIGN KEY (ReservationNo) REFERENCES Reservations(ReservationNo)
)

CREATE TABLE ReservationsLines(
ReservationsLinesId int IDENTITY (1,1) PRIMARY KEY,
ReservationNo INT NOT NULL,
FOREIGN KEY (ReservationNo) REFERENCES Reservations(ReservationNo),
RoomTypeId int NOT NULL,
FOREIGN KEY (RoomTypeId) REFERENCES RoomTypes (RoomTypeId),
Amount int NOT NULL
)

CREATE TABLE ExtraServices (
ExtraServiceId int IDENTITY (1,1) PRIMARY KEY,
ExtraServiceName nvarchar (50) NOT NULL,
ExtraServiceDescription nvarchar (300) NOT NULL,

)

CREATE TABLE Prices (
PriceTimeStamp DateTime PRIMARY KEY,
RoomTypeId int NOT NULL,
FOREIGN KEY (RoomTypeId) REFERENCES RoomTypes (RoomTypeId),
ExtraServiceId int NOT NULL,
FOREIGN KEY (ExtraServiceId) REFERENCES ExtraServices (ExtraServiceId),
Price SMALLMONEY NOT NULL,
)

CREATE TABLE Quantities (
ExtraServiceId int NOT NULL,
FOREIGN KEY (ExtraServiceId) REFERENCES ExtraServices (ExtraServiceId),
ReservationNo int NOT NULL,
PRIMARY KEY (ExtraServiceID, ReservationNo),
FOREIGN KEY (ReservationNo) REFERENCES Reservations (ReservationNo),
Quantity int NOT NULL,
)
