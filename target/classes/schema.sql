-- Relog Airline Check-in System Database Schema

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS check_ins;
DROP TABLE IF EXISTS passengers;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS aircraft;
DROP TABLE IF EXISTS flights;

-- Create Aircraft table
CREATE TABLE aircraft (
    aircraft_id INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(50) NOT NULL,
    total_seats INT NOT NULL,
    configuration_type VARCHAR(20) NOT NULL
);

-- Create Seats table
CREATE TABLE seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    aircraft_id INT NOT NULL,
    seat_number VARCHAR(5) NOT NULL,
    seat_class VARCHAR(20) NOT NULL,
    seat_type VARCHAR(10) NOT NULL, -- window, aisle, middle
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(aircraft_id),
    CONSTRAINT unique_seat_per_aircraft UNIQUE (aircraft_id, seat_number)
);

-- Create Flights table
CREATE TABLE flights (
    flight_id INT AUTO_INCREMENT PRIMARY KEY,
    flight_number VARCHAR(10) NOT NULL,
    origin VARCHAR(3) NOT NULL,
    destination VARCHAR(3) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    aircraft_id INT NOT NULL,
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(aircraft_id)
);

-- Create Bookings table
CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    pnr VARCHAR(6) NOT NULL UNIQUE,
    flight_id INT NOT NULL,
    booking_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id)
);

-- Create Passengers table
CREATE TABLE passengers (
    passenger_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);

-- Create Check-ins table
CREATE TABLE check_ins (
    checkin_id INT AUTO_INCREMENT PRIMARY KEY,
    passenger_id INT NOT NULL,
    flight_id INT NOT NULL,
    seat_id INT,
    checkin_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    boarding_pass_issued BOOLEAN NOT NULL DEFAULT FALSE,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    version INT NOT NULL DEFAULT 0, -- For optimistic locking
    FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id),
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id),
    FOREIGN KEY (seat_id) REFERENCES seats(seat_id),
    CONSTRAINT unique_seat_per_flight UNIQUE (flight_id, seat_id) -- Ensures one seat per flight is assigned to only one check-in
);

-- Create indexes for performance
CREATE INDEX idx_bookings_pnr ON bookings(pnr);
CREATE INDEX idx_checkins_flight_seat ON check_ins(flight_id, seat_id);
CREATE INDEX idx_checkins_passenger ON check_ins(passenger_id);

-- Insert sample data for testing

-- Insert Aircraft
INSERT INTO aircraft (model, total_seats, configuration_type) 
VALUES ('Boeing 737-800', 100, 'NARROW_BODY');

-- Insert Seats (simplified - just 100 seats)
-- Row 1
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '1A', 'BUSINESS', 'WINDOW');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '1B', 'BUSINESS', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '1C', 'BUSINESS', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '1D', 'BUSINESS', 'WINDOW');

-- Row 2
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '2A', 'BUSINESS', 'WINDOW');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '2B', 'BUSINESS', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '2C', 'BUSINESS', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '2D', 'BUSINESS', 'WINDOW');

-- Row 3
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '3A', 'BUSINESS', 'WINDOW');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '3B', 'BUSINESS', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '3C', 'BUSINESS', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '3D', 'BUSINESS', 'WINDOW');

-- Row 4
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '4A', 'BUSINESS', 'WINDOW');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '4B', 'BUSINESS', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '4C', 'BUSINESS', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '4D', 'BUSINESS', 'WINDOW');

-- Row 5
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '5A', 'BUSINESS', 'WINDOW');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '5B', 'BUSINESS', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '5C', 'BUSINESS', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '5D', 'BUSINESS', 'WINDOW');

-- Rows 6-25 (Economy)
-- Row 6
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '6A', 'ECONOMY', 'WINDOW');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '6B', 'ECONOMY', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '6C', 'ECONOMY', 'MIDDLE');
INSERT INTO seats (aircraft_id, seat_number, seat_class, seat_type) VALUES (1, '6D', 'ECONOMY', 'WINDOW');

-- Insert Flight
INSERT INTO flights (flight_number, origin, destination, departure_time, arrival_time, aircraft_id)
VALUES 
('RL101', 'DEL', 'BOM', '2025-06-20 10:00:00', '2025-06-20 12:00:00', 1),
('RL102', 'BOM', 'DEL', '2025-06-20 14:00:00', '2025-06-20 16:00:00', 1);

-- Insert Bookings
INSERT INTO bookings (pnr, flight_id, status)
VALUES 
('ABC123', 1, 'CONFIRMED'),
('DEF456', 1, 'CONFIRMED'),
('GHI789', 1, 'CONFIRMED'),
('JKL012', 1, 'CONFIRMED'),
('MNO345', 1, 'CONFIRMED');

-- Insert Passengers
INSERT INTO passengers (booking_id, first_name, last_name, email, phone)
VALUES 
(1, 'John', 'Doe', 'john.doe@example.com', '1234567890'),
(2, 'Jane', 'Smith', 'jane.smith@example.com', '2345678901'),
(3, 'Bob', 'Johnson', 'bob.johnson@example.com', '3456789012'),
(4, 'Alice', 'Williams', 'alice.williams@example.com', '4567890123'),
(5, 'Charlie', 'Brown', 'charlie.brown@example.com', '5678901234');

-- Insert Check-ins (some with seats, some without)
INSERT INTO check_ins (passenger_id, flight_id, seat_id, boarding_pass_issued, status)
VALUES 
(1, 1, 1, TRUE, 'COMPLETED'),  -- John has checked in and got seat 1A
(2, 1, 6, TRUE, 'COMPLETED');  -- Jane has checked in and got seat 2A

-- The other passengers haven't checked in yet