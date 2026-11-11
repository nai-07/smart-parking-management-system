USE smart_parking;

CREATE TABLE venues (
    venue_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    venue_type ENUM('MALL', 'RESTAURANT') NOT NULL,
    address VARCHAR(255),
    total_floors INT NOT NULL,
    has_valet BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE floors (
    floor_id INT AUTO_INCREMENT PRIMARY KEY,
    venue_id INT NOT NULL,
    floor_number INT NOT NULL,
    FOREIGN KEY (venue_id) REFERENCES venues(venue_id),
    UNIQUE (venue_id, floor_number)
);

CREATE TABLE slots (
    slot_id INT AUTO_INCREMENT PRIMARY KEY,
    floor_id INT NOT NULL,
    slot_number VARCHAR(20) NOT NULL,
    slot_type ENUM('BIKE', 'CAR', 'EV', 'HANDICAPPED', 'VIP') NOT NULL,
    status ENUM('FREE', 'BOOKED', 'OCCUPIED', 'RESERVED') DEFAULT 'FREE',
    FOREIGN KEY (floor_id) REFERENCES floors(floor_id),
    UNIQUE (floor_id, slot_number)
);

CREATE TABLE vehicles (
    vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
    plate_number VARCHAR(20) NOT NULL UNIQUE,
    vehicle_type ENUM('BIKE', 'CAR', 'EV') NOT NULL,
    owner_name VARCHAR(100)
);

CREATE TABLE tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id INT NOT NULL,
    venue_id INT NOT NULL,
    slot_id INT NOT NULL,
    entry_time DATETIME NOT NULL,
    exit_time DATETIME,
    status ENUM('ACTIVE', 'COMPLETED', 'CANCELLED') DEFAULT 'ACTIVE',
    qr_code VARCHAR(255),
    valet BOOLEAN DEFAULT FALSE,
    key_tag_number VARCHAR(50),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id),
    FOREIGN KEY (venue_id) REFERENCES venues(venue_id),
    FOREIGN KEY (slot_id) REFERENCES slots(slot_id)
);

CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method ENUM('CASH', 'UPI', 'CARD') NOT NULL,
    payment_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    discount_amount DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (ticket_id) REFERENCES tickets(ticket_id)
);

CREATE TABLE validations (
    validation_id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_id INT NOT NULL,
    validation_code VARCHAR(50) NOT NULL,
    bill_amount DECIMAL(10,2) NOT NULL,
    discount_amount DECIMAL(10,2) DEFAULT 0,
    validated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ticket_id) REFERENCES tickets(ticket_id)
);

CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    venue_id INT NOT NULL,
    slot_id INT NOT NULL,
    vehicle_number VARCHAR(20) NOT NULL,
    table_booking_time DATETIME,
    reservation_start DATETIME NOT NULL,
    reservation_end DATETIME NOT NULL,
    status ENUM('ACTIVE', 'USED', 'CANCELLED', 'EXPIRED') DEFAULT 'ACTIVE',
    FOREIGN KEY (venue_id) REFERENCES venues(venue_id),
    FOREIGN KEY (slot_id) REFERENCES slots(slot_id)
);