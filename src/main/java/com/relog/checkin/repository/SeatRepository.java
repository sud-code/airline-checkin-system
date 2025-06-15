package com.relog.checkin.repository;

import com.relog.checkin.model.Aircraft;
import com.relog.checkin.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    
    List<Seat> findByAircraft(Aircraft aircraft);
    
    Optional<Seat> findByAircraftAndSeatNumber(Aircraft aircraft, String seatNumber);
    
    @Query("SELECT s FROM Seat s WHERE s.aircraft.aircraftId = ?1 AND s.seatId NOT IN ?2")
    List<Seat> findAvailableSeatsByAircraftIdAndOccupiedSeatIds(Long aircraftId, List<Long> occupiedSeatIds);
}