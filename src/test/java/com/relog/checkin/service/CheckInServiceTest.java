package com.relog.checkin.service;

import com.relog.checkin.dto.SeatDTO;
import com.relog.checkin.dto.SeatSelectionRequest;
import com.relog.checkin.exception.ResourceNotFoundException;
import com.relog.checkin.exception.SeatAlreadyOccupiedException;
import com.relog.checkin.model.*;
import com.relog.checkin.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class CheckInServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private CheckInRepository checkInRepository;

    @InjectMocks
    private CheckInServiceImpl checkInService;

    private Flight flight;
    private Booking booking;
    private Passenger passenger;
    private Seat seat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        Aircraft aircraft = new Aircraft();
        aircraft.setAircraftId(1L);
        aircraft.setModel("Boeing 737-800");
        aircraft.setTotalSeats(100);
        aircraft.setConfigurationType("NARROW_BODY");

        flight = new Flight();
        flight.setFlightId(1L);
        flight.setFlightNumber("RL101");
        flight.setOrigin("DEL");
        flight.setDestination("BOM");
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        flight.setAircraft(aircraft);

        booking = new Booking();
        booking.setBookingId(1L);
        booking.setPnr("ABC123");
        booking.setFlight(flight);
        booking.setBookingTime(LocalDateTime.now().minusDays(5));
        booking.setStatus("CONFIRMED");

        passenger = new Passenger();
        passenger.setPassengerId(1L);
        passenger.setBooking(booking);
        passenger.setFirstName("John");
        passenger.setLastName("Doe");
        passenger.setEmail("john.doe@example.com");
        passenger.setPhone("1234567890");

        seat = new Seat();
        seat.setSeatId(10L);
        seat.setAircraft(aircraft);
        seat.setSeatNumber("5A");
        seat.setSeatClass("BUSINESS");
        seat.setSeatType("WINDOW");
    }

    @Test
    void validatePnr_ValidPnr_ReturnsFlightId() {
        // Arrange
        when(bookingRepository.findByPnr("ABC123")).thenReturn(Optional.of(booking));

        // Act
        Long flightId = checkInService.validatePnr("ABC123");

        // Assert
        assertEquals(1L, flightId);
    }

    @Test
    void validatePnr_InvalidPnr_ThrowsException() {
        // Arrange
        when(bookingRepository.findByPnr("INVALID")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            checkInService.validatePnr("INVALID");
        });
    }

    @Test
    void getAvailableSeats_ValidFlightId_ReturnsAvailableSeats() {
        // Arrange
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(checkInRepository.findAllOccupiedSeatIdsByFlightId(1L)).thenReturn(Arrays.asList(1L, 2L));
        
        List<Seat> availableSeats = Arrays.asList(seat);
        when(seatRepository.findAvailableSeatsByAircraftIdAndOccupiedSeatIds(eq(1L), anyList()))
                .thenReturn(availableSeats);

        // Act
        List<SeatDTO> result = checkInService.getAvailableSeats(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("5A", result.get(0).getSeatNumber());
        assertEquals("BUSINESS", result.get(0).getSeatClass());
        assertEquals("WINDOW", result.get(0).getSeatType());
        assertTrue(result.get(0).isAvailable());
    }

    @Test
    void selectSeat_ValidRequest_ReturnsSeatDTO() {
        // Arrange
        SeatSelectionRequest request = new SeatSelectionRequest("ABC123", 1L, 10L);
        
        when(bookingRepository.findByPnr("ABC123")).thenReturn(Optional.of(booking));
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(seatRepository.findById(10L)).thenReturn(Optional.of(seat));
        when(checkInRepository.existsByFlightAndSeat(flight, seat)).thenReturn(false);
        when(passengerRepository.findByBooking(booking)).thenReturn(Arrays.asList(passenger));
        when(checkInRepository.findByPassenger(passenger)).thenReturn(Optional.empty());

        // Act
        SeatDTO result = checkInService.selectSeat(request);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("5A", result.getSeatNumber());
        assertEquals("BUSINESS", result.getSeatClass());
        assertEquals("WINDOW", result.getSeatType());
        assertFalse(result.isAvailable());
    }

    @Test
    void selectSeat_SeatAlreadyOccupied_ThrowsException() {
        // Arrange
        SeatSelectionRequest request = new SeatSelectionRequest("ABC123", 1L, 10L);
        
        when(bookingRepository.findByPnr("ABC123")).thenReturn(Optional.of(booking));
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(seatRepository.findById(10L)).thenReturn(Optional.of(seat));
        when(checkInRepository.existsByFlightAndSeat(flight, seat)).thenReturn(true);

        // Act & Assert
        assertThrows(SeatAlreadyOccupiedException.class, () -> {
            checkInService.selectSeat(request);
        });
    }
}