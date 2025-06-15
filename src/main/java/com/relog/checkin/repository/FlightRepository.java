package com.relog.checkin.repository;

import com.relog.checkin.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
    List<Flight> findByFlightNumber(String flightNumber);
    
    List<Flight> findByOriginAndDestination(String origin, String destination);
    
    List<Flight> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);
}