-- =========================
-- INSERT INTO PersonTypes
-- =========================
INSERT INTO PersonTypes (PersonTypeName) VALUES
('Guest'),
('Receptionist'),
('Housekeeping'),
('Manager'),
('Owner');
GO

-- =========================
-- INSERT INTO Address
-- =========================
INSERT INTO PersonAddress (StreetName, HouseNo, FloorNo, ZipCode, Country) VALUES
('Main Street', 101, 1, 12345, 'USA'),
('Elm Street', 202, 2, 23456, 'USA'),
('Maple Avenue', 303, 3, 34567, 'USA'),
('Oak Boulevard', 404, 4, 45678, 'USA'),
('Pine Road', 505, 5, 56789, 'USA'),
('Grove Street', 10, 9, 87630, 'USA'),
('Ved Volden', 004, 4, 94000, 'DENMARK'),
('Dragon Keep', 876, 9, 34444, 'SKYRIM'),
('Vader Street', 500, 7, 45800, 'MUSTAFAR'),
('Harvey Street', 101, 1, 123451, 'FIC USA'),
('Father Christ Street', 209, 23, 243456, 'NORTH POLE'),
('Ashlan Street', 303, 3, 34567, 'NARNIA'),
('Death Star', 444, 4, 44444, 'GALAXY'),
('Joker Street', 787, 14, 93810, 'AMERICAN'),
('Los Santo Street', 10, 9, 87630, 'USA'),
('Monk Way', 1004, 43, 194000, 'CHINA'),
('Whiterun Street', 876, 9, 34444, 'SKYRIM'),
('Salt Street', 500, 7, 45800, 'MUSTAFAR'),
('Green Street', 500, 7, 45800, 'LALALAND'),
('Bagel Hill', 768, 5, 56789, 'CANADA');
GO

-- =========================
-- INSERT INTO Persons
-- =========================
INSERT INTO Persons (PhoneNo, FName, LName, Email, AddressId, PersonTypeId) VALUES
('1000001', 'John', 'Doe', 'john.doe@example.com', 1, 1),
('1000002', 'Jane', 'Smith', 'jane.smith@example.com', 2, 1),
('1000003', 'Alice', 'Johnson', 'alice.johnson@example.com', 3, 1),
('1000004', 'Charlie', 'Davis', 'charlie.davis@example.com', 4, 1),
('1000005', 'Darth', 'Vader', 'vader.isunhappy@example.com', 5, 1),
('1000006', 'Martin', 'Loas', 'martin.davis@example.com', 6, 1),
('1000007', 'Johnny', 'Silverhand', 'silverhand.doe@example.com', 7, 1),
('1000008', 'Peter', 'Petrieli', 'petrieli@example.com', 8, 1),
('1000009', 'Goku', 'Son', 'dragonball@example.com', 9, 1),
('1000010', 'Luffy', 'Monkey', 'meat@example.com', 10, 1),
('1000011', 'Harry', 'Louis', 'charlie.davis@example.com', 11, 1),
('1000012', 'Darth', 'Krayth', 'kraytishappy@example.com', 12, 1),
('1000013', 'Marlie', 'Mavis', 'marlie.davis@example.com', 13, 1),
('1000014', 'Moki', 'Gobin', 'goblinman@example.com', 14, 1),
('1000015', 'Gale', 'Dekarios', 'mystrafanboy@example.com', 15, 1),
('1000016', 'Matahari', 'Jessie', 'jessie@example.com', 16, 1),
('1000017', 'Jenevelle', 'Hallowleaf', 'shadowheart@example.com', 17, 1),
('1000018', 'Palpatine', 'Senate', 'unlimitedpower@example.com', 18, 1),
('1000019', 'Darth', 'Maul', 'nolegs@example.com', 19, 1),
('1000020', 'Eddard', 'Stark', 'no.head@example.com', 20, 1)
;
GO

-- =========================
-- INSERT INTO Reservations
-- =========================
INSERT INTO Reservations (ReservationStartDate, ReservationEndDate, CheckedIn, CheckedOut, PayAtLocation, IsConfirmationSent, IsPaid, ReservationNote, PhoneNo) VALUES
('2025-05-01', '2025-05-05', 0, 0, 1, 1, 0, 'Early check-in requested', 1000001),
('2025-05-10', '2025-05-15', 0, 0, 0, 1, 1, 'Allergic to peanuts', 1000002),
('2025-06-01', '2025-06-03', 0, 0, 1, 1, 0, 'Late checkout approved', 1000003),
('2025-06-10', '2025-06-15', 0, 0, 1, 1, 0, 'Anniversary stay', 1000004),
('2025-07-01', '2025-07-07', 0, 0, 0, 1, 1, 'Needs baby crib', 1000005),
('2025-05-11', '2025-05-25', 0, 0, 1, 1, 0, 'Early check-in requested', 1000006),
('2025-05-10', '2025-05-15', 0, 0, 0, 1, 1, 'Allergic to peanuts', 1000007),
('2025-06-01', '2025-06-03', 0, 0, 1, 1, 0, 'Late checkout approved', 1000008),
('2025-06-10', '2025-06-15', 0, 0, 1, 1, 0, 'Anniversary stay', 1000009),
('2025-07-01', '2025-07-07', 0, 0, 0, 1, 1, 'Needs baby crib', 1000010),
('2025-05-01', '2025-05-05', 0, 0, 1, 1, 0, 'Early check-in requested', 1000011),
('2025-05-10', '2025-05-15', 0, 0, 0, 1, 1, 'Allergic to peanuts', 1000012),
('2025-06-01', '2025-06-03', 0, 0, 1, 1, 0, 'Late checkout approved', 1000013),
('2025-06-10', '2025-06-15', 0, 0, 1, 1, 0, 'Anniversary stay', 1000014),
('2025-07-01', '2025-07-07', 0, 0, 0, 1, 0, 'Needs baby crib', 1000015),
('2025-05-01', '2025-05-05', 0, 0, 1, 1, 0, 'Early check-in requested', 1000016),
('2025-05-10', '2025-05-15', 0, 0, 0, 1, 1, 'Allergic to peanuts', 1000017),
('2025-06-01', '2025-06-03', 0, 0, 1, 1, 0, 'Late checkout approved', 1000018),
('2025-06-10', '2025-06-15', 0, 0, 1, 1, 0, 'Anniversary stay', 1000019),
('2025-07-01', '2025-07-07', 0, 0, 0, 1, 1, 'Needs baby crib', 1000020);
GO

-- =========================
-- INSERT INTO RoomTypes
-- =========================
INSERT INTO RoomTypes (RoomTypeName, RoomTypeDescription, BedSpace) VALUES
('Single', 'Single bed room', 1),
('Double', 'Double bed room', 2),
('Suite', 'Luxury suite', 4),
('Family', 'Family room', 5),
('Penthouse', 'Top floor luxury suite', 6);
GO

-- =========================
-- INSERT INTO Rooms
-- =========================
INSERT INTO Rooms (RoomNO,RoomInfo, IsOccupied, RoomTypeId) VALUES
('001','single room', 0, 1),
('002','single room', 0, 1),
('003','single room', 0, 1),
('004','single room', 0, 1),
('005','single room', 0, 1),
('006','single room', 0, 1),
('007','single room', 0, 1),
('008','single room', 0, 1),
('009','single room', 0, 1),
('010','single room', 0, 1),
('011','single room', 0, 1),
('012','single room', 0, 1),
('013','single room', 0, 1),
('014','single room', 0, 1),
('015','single room', 0, 1),
('016','single room', 0, 1),
('017','single room', 0, 1),
('018','single room', 0, 1),
('019','single room', 0, 1),
('020','single room', 0, 1),
('021','single room', 0, 1),
('022','single room', 0, 1),
('023','single room', 0, 1),
('024','single room', 0, 1),
('025','single room', 0, 1),
('101','Double bed room', 0, 2),
('102','Double bed room', 0, 2),
('103','Double bed room', 0, 2),
('104','Double bed room', 0, 2),
('105','Double bed room', 0, 2),
('106','Double bed room', 0, 2),
('107','Double bed room', 0, 2),
('108','Double bed room', 0, 2),
('109','Double bed room', 0, 2),
('110','Double bed room', 0, 2),
('111','Double bed room', 0, 2),
('112','Double bed room', 0, 2),
('113','Double bed room', 0, 2),
('114','Double bed room', 0, 2),
('115','Double bed room', 0, 2),
('116','Double bed room', 0, 2),
('117','Double bed room', 0, 2),
('118','Double bed room', 0, 2),
('119','Double bed room', 0, 2),
('120','Double bed room', 0, 2),
('121','Double bed room', 0, 2),
('122','Double bed room', 0, 2),
('123','Double bed room', 0, 2),
('124','Double bed room', 0, 2),
('125','Double bed room', 0, 2),
('201','Double bed room', 0, 2),
('202','Double bed room', 0, 2),
('203','Double bed room', 0, 2),
('204','Double bed room', 0, 2),
('205','Double bed room', 0, 2),
('206','Double bed room', 0, 2),
('207','Double bed room', 0, 2),
('208','Double bed room', 0, 2),
('209','Double bed room', 0, 2),
('210','Double bed room', 0, 2),
('211','Double bed room', 0, 2),
('212','Double bed room', 0, 2),
('213','Double bed room', 0, 2),
('214','Double bed room', 0, 2),
('215','Double bed room', 0, 2),
('216','Double bed room', 0, 2),
('217','Double bed room', 0, 2),
('218','Double bed room', 0, 2),
('219','Double bed room', 0, 2),
('220','Double bed room', 0, 2),
('221','Double bed room', 0, 2),
('222','Double bed room', 0, 2),
('223','Double bed room', 0, 2),
('224','Double bed room', 0, 2),
('225','Double bed room', 0, 2),
('501','luxus room', 0, 3),
('502','luxus room', 0, 3),
('503','luxus room', 0, 3),
('504','luxus room', 0, 3),
('505','luxus room', 0, 3),
('506','luxus room', 0, 3),
('507','luxus room', 0, 3),
('508','luxus room', 0, 3),
('509','luxus room', 0, 3),
('510','luxus room', 0, 3),
('301','Family room', 0, 4),
('302','Family room', 0, 4),
('303','Family room', 0, 4),
('304','Family room', 0, 4),
('305','Family room', 0, 4),
('306','Family room', 0, 4),
('307','Family room', 0, 4),
('308','Family room', 0, 4),
('309','Family room', 0, 4),
('310','Family room', 0, 4),
('311','Family room', 0, 4),
('312','Family room', 0, 4),
('313','Family room', 0, 4),
('314','Family room', 0, 4),
('315','Family room', 0, 4),
('316','Family room', 0, 4),
('317','Family room', 0, 4),
('318','Family room', 0, 4),
('319','Family room', 0, 4),
('320','Family room', 0, 4),
('321','Family room', 0, 4),
('322','Family room', 0, 4),
('323','Family room', 0, 4),
('324','Family room', 0, 4),
('325','Family room', 0, 4),
('401','Family room', 0, 4),
('402','Family room', 0, 4),
('403','Family room', 0, 4),
('404','Family room', 0, 4),
('405','Family room', 0, 4),
('406','Family room', 0, 4),
('407','Family room', 0, 4),
('408','Family room', 0, 4),
('409','Family room', 0, 4),
('410','Family room', 0, 4),
('411','Family room', 0, 4),
('412','Family room', 0, 4),
('413','Family room', 0, 4),
('414','Family room', 0, 4),
('415','Family room', 0, 4),
('416','Family room', 0, 4),
('417','Family room', 0, 4),
('418','Family room', 0, 4),
('419','Family room', 0, 4),
('420','Family room', 0, 4),
('421','Family room', 0, 4),
('422','Family room', 0, 4),
('423','Family room', 0, 4),
('424','Family room', 0, 4),
('425','Family room', 0, 4),
('601','Penthouse with jacuzzi', 0, 5),
('602','Penthouse with jacuzzi', 0, 5);
GO

-- =========================
-- INSERT INTO ReservationsLines
-- =========================
INSERT INTO ReservationsLines (ReservationNo, RoomTypeId, Amount) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 3, 1),
(4, 4, 1),
(5, 1, 1),
(6, 2, 2),
(7, 3, 1),
(8, 4, 2),
(9, 1, 1),
(10, 2, 1),
(11, 3, 2),
(12, 4, 1),
(13, 1, 2),
(14, 2, 1),
(15, 3, 1),
(16, 4, 1),
(17, 1, 1),
(18, 2, 2),
(19, 3, 1),
(20, 4, 1);
GO


-- =========================
-- INSERT INTO ExtraServices
-- =========================
INSERT INTO ExtraServices (ExtraServiceName, ExtraServiceDescription) VALUES
('Breakfast', 'Buffet breakfast included'),
('Airport Shuttle', 'Transport to and from airport'),
('Spa Access', 'Unlimited access to spa'),
('Late Checkout', 'Checkout extended to 4 PM'),
('Parking', 'Secure parking spot');
GO

-- =========================
-- INSERT INTO Prices (with DATETIME for PriceTimeStamp)
-- =========================
INSERT INTO Prices (PriceTimeStamp, RoomTypeId, ExtraServiceId, Price) VALUES
('2025-04-01 08:00:00', 1, 1, 15.00),
('2025-04-02 09:00:00', 2, 2, 25.00),
('2025-04-03 10:00:00', 3, 3, 350.00),
('2025-04-04 11:00:00', 4, 4, 100.00),
('2025-04-05 12:00:00', 5, 5, 1000.00);
GO

-- =========================
-- INSERT INTO Quantity
-- =========================
INSERT INTO Quantities (ExtraServiceId, ReservationNo, Quantity) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 3, 3),
(4, 4, 1),
(5, 5, 2);
GO
