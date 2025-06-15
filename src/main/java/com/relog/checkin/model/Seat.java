package com.relog.checkin.model;

import javax.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long seatId;
    
    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;
    
    @Column(name = "seat_number", nullable = false)
    private String seatNumber;
    
    @Column(name = "seat_class", nullable = false)
    private String seatClass;
    
    @Column(name = "seat_type", nullable = false)
    private String seatType;
    
    // Transient field to track if seat is available (not mapped to database)
    @Transient
    private boolean available = true;
    
    // Constructors
    public Seat() {
    }
    
    public Seat(Long seatId, Aircraft aircraft, String seatNumber, String seatClass, String seatType, boolean available) {
        this.seatId = seatId;
        this.aircraft = aircraft;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.seatType = seatType;
        this.available = available;
    }
    
    // Getters and setters
    public Long getSeatId() {
        return seatId;
    }
    
    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }
    
    public Aircraft getAircraft() {
        return aircraft;
    }
    
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
    
    public String getSeatNumber() {
        return seatNumber;
    }
    
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
    
    public String getSeatClass() {
        return seatClass;
    }
    
    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }
    
    public String getSeatType() {
        return seatType;
    }
    
    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
}