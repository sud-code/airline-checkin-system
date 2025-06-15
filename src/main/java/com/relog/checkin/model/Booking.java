package com.relog.checkin.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings")
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;
    
    @Column(name = "pnr", nullable = false, unique = true)
    private String pnr;
    
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
    
    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Passenger> passengers;
    
    // Constructors
    public Booking() {
    }
    
    public Booking(Long bookingId, String pnr, Flight flight, LocalDateTime bookingTime, String status, List<Passenger> passengers) {
        this.bookingId = bookingId;
        this.pnr = pnr;
        this.flight = flight;
        this.bookingTime = bookingTime;
        this.status = status;
        this.passengers = passengers;
    }
    
    // Getters and setters
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    public String getPnr() {
        return pnr;
    }
    
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
    
    public Flight getFlight() {
        return flight;
    }
    
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<Passenger> getPassengers() {
        return passengers;
    }
    
    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
}