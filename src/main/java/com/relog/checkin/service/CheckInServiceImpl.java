package com.relog.checkin.service;

import com.relog.checkin.dto.BoardingPassDTO;
import com.relog.checkin.dto.SeatDTO;
import com.relog.checkin.dto.SeatSelectionRequest;
import com.relog.checkin.exception.ResourceNotFoundException;
import com.relog.checkin.exception.SeatAlreadyOccupiedException;
import com.relog.checkin.model.*;
import com.relog.checkin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CheckInServiceImpl implements CheckInService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final PassengerRepository passengerRepository;
    private final CheckInRepository checkInRepository;

    @Autowired
    public CheckInServiceImpl(BookingRepository bookingRepository,
                             FlightRepository flightRepository,
                             SeatRepository seatRepository,
                             PassengerRepository passengerRepository,
                             CheckInRepository checkInRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        this.passengerRepository = passengerRepository;
        this.checkInRepository = checkInRepository;
    }

    @Override
    public Long validatePnr(String pnr) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "pnr", pnr));
        
        return booking.getFlight().getFlightId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatDTO> getAvailableSeats(Long flightId) throws ResourceNotFoundException {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId));
        
        // Get all occupied seat IDs for this flight
        List<Long> occupiedSeatIds = checkInRepository.findAllOccupiedSeatIdsByFlightId(flightId);
        
        // Get all seats for the aircraft and filter out occupied ones
        List<Seat> availableSeats = seatRepository.findAvailableSeatsByAircraftIdAndOccupiedSeatIds(
                flight.getAircraft().getAircraftId(), occupiedSeatIds);
        
        // Convert to DTOs
        return availableSeats.stream()
                .map(seat -> new SeatDTO(
                        seat.getSeatId(),
                        seat.getSeatNumber(),
                        seat.getSeatClass(),
                        seat.getSeatType(),
                        true))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SeatDTO selectSeat(SeatSelectionRequest request) 
            throws ResourceNotFoundException, SeatAlreadyOccupiedException {
        
        // Validate booking
        Booking booking = bookingRepository.findByPnr(request.getPnr())
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "pnr", request.getPnr()));
        
        // Validate flight
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", request.getFlightId()));
        
        // Validate seat
        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat", "id", request.getSeatId()));
        
        // Check if seat is already occupied (with pessimistic locking)
        if (checkInRepository.existsByFlightAndSeat(flight, seat)) {
            throw new SeatAlreadyOccupiedException(seat.getSeatNumber(), flight.getFlightNumber());
        }
        
        // Get passenger
        List<Passenger> passengers = passengerRepository.findByBooking(booking);
        if (passengers.isEmpty()) {
            throw new ResourceNotFoundException("Passenger", "booking", booking.getBookingId());
        }
        Passenger passenger = passengers.get(0); // For simplicity, we're assuming one passenger per booking
        
        // Check if passenger already has a check-in
        Optional<CheckIn> existingCheckIn = checkInRepository.findByPassenger(passenger);
        
        CheckIn checkIn;
        if (existingCheckIn.isPresent()) {
            // Update existing check-in
            checkIn = existingCheckIn.get();
            checkIn.setSeat(seat);
            checkIn.setStatus("SEAT_SELECTED");
        } else {
            // Create new check-in
            checkIn = new CheckIn();
            checkIn.setPassenger(passenger);
            checkIn.setFlight(flight);
            checkIn.setSeat(seat);
            checkIn.setCheckinTime(LocalDateTime.now());
            checkIn.setBoardingPassIssued(false);
            checkIn.setStatus("SEAT_SELECTED");
            checkIn.setVersion(0);
        }
        
        checkInRepository.save(checkIn);
        
        return new SeatDTO(
                seat.getSeatId(),
                seat.getSeatNumber(),
                seat.getSeatClass(),
                seat.getSeatType(),
                false);
    }

    @Override
    @Transactional
    public BoardingPassDTO generateBoardingPass(String pnr) throws ResourceNotFoundException {
        // Validate booking
        Booking booking = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "pnr", pnr));
        
        // Get passenger
        List<Passenger> passengers = passengerRepository.findByBooking(booking);
        if (passengers.isEmpty()) {
            throw new ResourceNotFoundException("Passenger", "booking", booking.getBookingId());
        }
        Passenger passenger = passengers.get(0);
        
        // Get check-in
        CheckIn checkIn = checkInRepository.findByPassenger(passenger)
                .orElseThrow(() -> new ResourceNotFoundException("Check-in", "passenger", passenger.getPassengerId()));
        
        // Validate seat selection
        if (checkIn.getSeat() == null) {
            throw new ResourceNotFoundException("Seat selection", "passenger", passenger.getPassengerId());
        }
        
        // Update check-in status
        checkIn.setBoardingPassIssued(true);
        checkIn.setStatus("COMPLETED");
        checkInRepository.save(checkIn);
        
        Flight flight = checkIn.getFlight();
        Seat seat = checkIn.getSeat();
        
        // Calculate boarding time (30 minutes before departure)
        LocalDateTime boardingTime = flight.getDepartureTime().minusMinutes(30);
        
        // Generate boarding pass
        return BoardingPassDTO.builder()
                .passengerName(passenger.getFirstName() + " " + passenger.getLastName())
                .flightNumber(flight.getFlightNumber())
                .origin(flight.getOrigin())
                .destination(flight.getDestination())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .seatNumber(seat.getSeatNumber())
                .seatClass(seat.getSeatClass())
                .gate("A1") // Hardcoded for simplicity
                .boardingTime(boardingTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                .pnr(booking.getPnr())
                .barcode(generateBarcode())
                .build();
    }
    
    private String generateBarcode() {
        // Simple barcode generation for demo purposes
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}