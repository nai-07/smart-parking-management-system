USE smart_parking;

-- MALLS
INSERT INTO venues (name, venue_type, address, total_floors, has_valet)
VALUES
('Pacific Mall', 'MALL', 'New Delhi', 3, FALSE),
('DLF Mall of India', 'MALL', 'Noida', 4, FALSE);

-- RESTAURANT
INSERT INTO venues (name, venue_type, address, total_floors, has_valet)
VALUES
('The Grand Restaurant', 'RESTAURANT', 'Connaught Place, New Delhi', 1, TRUE);

-- FLOORS
INSERT INTO floors (venue_id, floor_number)
VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(3, 1);

-- PACIFIC MALL - FLOOR 1
INSERT INTO slots (floor_id, slot_number, slot_type)
VALUES
(1, 'P1-B01', 'BIKE'),
(1, 'P1-C01', 'CAR'),
(1, 'P1-C02', 'CAR'),
(1, 'P1-E01', 'EV'),
(1, 'P1-H01', 'HANDICAPPED'),
(1, 'P1-V01', 'VIP');

-- PACIFIC MALL - FLOOR 2
INSERT INTO slots (floor_id, slot_number, slot_type)
VALUES
(2, 'P2-B01', 'BIKE'),
(2, 'P2-C01', 'CAR'),
(2, 'P2-C02', 'CAR'),
(2, 'P2-E01', 'EV'),
(2, 'P2-H01', 'HANDICAPPED'),
(2, 'P2-V01', 'VIP');

-- PACIFIC MALL - FLOOR 3
INSERT INTO slots (floor_id, slot_number, slot_type)
VALUES
(3, 'P3-B01', 'BIKE'),
(3, 'P3-C01', 'CAR'),
(3, 'P3-C02', 'CAR'),
(3, 'P3-E01', 'EV'),
(3, 'P3-H01', 'HANDICAPPED'),
(3, 'P3-V01', 'VIP');

-- DLF MALL OF INDIA - FLOOR 1
INSERT INTO slots (floor_id, slot_number, slot_type)
VALUES
(4, 'D1-B01', 'BIKE'),
(4, 'D1-C01', 'CAR'),
(4, 'D1-C02', 'CAR'),
(4, 'D1-E01', 'EV'),
(4, 'D1-H01', 'HANDICAPPED'),
(4, 'D1-V01', 'VIP');

-- DLF MALL OF INDIA - FLOOR 2
INSERT INTO slots (floor_id, slot_number, slot_type)
VALUES
(5, 'D2-B01', 'BIKE'),
(5, 'D2-C01', 'CAR'),
(5, 'D2-C02', 'CAR'),
(5, 'D2-E01', 'EV'),
(5, 'D2-H01', 'HANDICAPPED'),
(5, 'D2-V01', 'VIP');

-- DLF MALL OF INDIA - FLOOR 3
INSERT INTO slots (floor_id, slot_number, slot_type)
VALUES
(6, 'D3-B01', 'BIKE'),
(6, 'D3-C01', 'CAR'),
(6, 'D3-C02', 'CAR'),
(6, 'D3-E01', 'EV'),
(6, 'D3-H01', 'HANDICAPPED'),
(6, 'D3-V01', 'VIP');

-- DLF MALL OF INDIA - FLOOR 4
INSERT INTO slots (floor_id, slot_number, slot_type)
VALUES
(7, 'D4-B01', 'BIKE'),
(7, 'D4-C01', 'CAR'),
(7, 'D4-C02', 'CAR'),
(7, 'D4-E01', 'EV'),
(7, 'D4-H01', 'HANDICAPPED'),
(7, 'D4-V01', 'VIP');

-- RESTAURANT
INSERT INTO slots (floor_id, slot_number, slot_type)
VALUES
(8, 'R1-B01', 'BIKE'),
(8, 'R1-C01', 'CAR'),
(8, 'R1-C02', 'CAR'),
(8, 'R1-E01', 'EV'),
(8, 'R1-H01', 'HANDICAPPED'),
(8, 'R1-V01', 'VIP');