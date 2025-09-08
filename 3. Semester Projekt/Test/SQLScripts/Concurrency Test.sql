
INSERT INTO PersonAddress (StreetName, HouseNo, FloorNo, ZipCode, Country) 
VALUES
('test vej', 101, '1th', 1234, 'Danmark')
DECLARE @AID int = SCOPE_IDENTITY()

INSERT INTO Persons (PhoneNo, FName, LName, Email, AddressId, PersonTypeId)
VALUES
('38383838', 'Sam', 'Test', 'Test@mail.test', @AID, 1)


INSERT INTO Reservations (ReservationStartDate, ReservationEndDate, CheckedIn, CheckedOut, PayAtLocation, IsConfirmationSent, IsPaid, ReservationNote, PhoneNo) 
VALUES
('2025-05-01', '2026-05-05', 0, 0, 1, 1, 0, 'Early check-in requested', '38383838')
DECLARE @RID int = SCOPE_IDENTITY()

INSERT INTO ReservationsLines (ReservationNo, RoomTypeId, Amount) 
VALUES
(@RID, 1 ,1)

INSERT INTO ReservationsLines (ReservationNo, RoomTypeId, Amount) 
VALUES
(@RID, 2 ,1)
INSERT INTO ReservationsLines (ReservationNo, RoomTypeId, Amount) 
VALUES
(@RID, 3 ,1)
INSERT INTO ReservationsLines (ReservationNo, RoomTypeId, Amount) 
VALUES
(@RID, 4 ,1)
INSERT INTO ReservationsLines (ReservationNo, RoomTypeId, Amount) 
VALUES
(@RID, 5 ,1)