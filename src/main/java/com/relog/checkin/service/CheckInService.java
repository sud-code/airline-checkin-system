package com.relog.checkin.service;

import com.relog.checkin.dto.BoardingPassDTO;
import com.relog.checkin.dto.SeatDTO;
import com.relog.checkin.dto.SeatSelectionRequest;
import com.relog.checkin.exception.SeatAlreadyOccupiedException;
import com.relog.checkin.exception.ResourceNotFoundException;

import java.util.List;

public interface CheckInService {
    
    /**
     * Validates a PNR and returns the associated flight details
     * 
     * @param pnr The PNR to validate
     * @return The flight ID if valid
     * @throws ResourceNotFoundException if PNR is invalid
     */
    Long validatePnr(String pnr) throws ResourceNotFoundException;
    
    /**
     * Gets all available seats for a flight
     * 
     * @param flightId The flight ID
     * @return List of available seats
     * @throws ResourceNotFoundException if flight not found
     */
    List<SeatDTO> getAvailableSeats(Long flightId) throws ResourceNotFoundException;
    
    /**
     * Selects a seat for a passenger
     * 
     * @param request The seat selection request
     * @return The selected seat
     * @throws ResourceNotFoundException if flight or seat not found
     * @throws SeatAlreadyOccupiedException if seat is already occupied
     */
    SeatDTO selectSeat(SeatSelectionRequest request) 
            throws ResourceNotFoundException, SeatAlreadyOccupiedException;
    
    /**
     * Generates a boarding pass for a passenger
     * 
     * @param pnr The PNR
     * @return The boarding pass
     * @throws ResourceNotFoundException if PNR is invalid or seat not selected
     */
    BoardingPassDTO generateBoardingPass(String pnr) throws ResourceNotFoundException;
}