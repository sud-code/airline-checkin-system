package com.relog.checkin.repository;

import com.relog.checkin.model.Booking;
import com.relog.checkin.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    
    List<Passenger> findByBooking(Booking booking);
    
    List<Passenger> findByLastNameAndFirstName(String lastName, String firstName);
}