package com.relog.checkin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SeatAlreadyOccupiedException extends RuntimeException {
    
    public SeatAlreadyOccupiedException(String message) {
        super(message);
    }
    
    public SeatAlreadyOccupiedException(String seatNumber, String flightNumber) {
        super(String.format("Seat %s on flight %s is already occupied. Please select another seat.", 
                seatNumber, flightNumber));
    }
}