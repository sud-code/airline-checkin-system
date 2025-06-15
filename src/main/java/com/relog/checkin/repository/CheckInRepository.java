package com.relog.checkin.repository;

import com.relog.checkin.model.CheckIn;
import com.relog.checkin.model.Flight;
import com.relog.checkin.model.Passenger;
import com.relog.checkin.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    
    List<CheckIn> findByFlight(Flight flight);
    
    Optional<CheckIn> findByPassenger(Passenger passenger);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CheckIn c WHERE c.flight = ?1 AND c.seat = ?2")
    Optional<CheckIn> findByFlightAndSeatWithLock(Flight flight, Seat seat);
    
    boolean existsByFlightAndSeat(Flight flight, Seat seat);
    
    @Query("SELECT c.seat.seatId FROM CheckIn c WHERE c.flight.flightId = ?1 AND c.seat IS NOT NULL")
    List<Long> findAllOccupiedSeatIdsByFlightId(Long flightId);
}