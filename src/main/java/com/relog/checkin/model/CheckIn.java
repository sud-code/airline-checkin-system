package com.relog.checkin.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "check_ins")
public class CheckIn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkin_id")
    private Long checkinId;
    
    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;
    
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
    
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    
    @Column(name = "checkin_time", nullable = false)
    private LocalDateTime checkinTime;
    
    @Column(name = "boarding_pass_issued", nullable = false)
    private Boolean boardingPassIssued;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Version
    @Column(name = "version")
    private Integer version;
    
    // Constructors
    public CheckIn() {
    }
    
    public CheckIn(Long checkinId, Passenger passenger, Flight flight, Seat seat, 
                  LocalDateTime checkinTime, Boolean boardingPassIssued, String status, Integer version) {
        this.checkinId = checkinId;
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
        this.checkinTime = checkinTime;
        this.boardingPassIssued = boardingPassIssued;
        this.status = status;
        this.version = version;
    }
    
    // Getters and setters
    public Long getCheckinId() {
        return checkinId;
    }
    
    public void setCheckinId(Long checkinId) {
        this.checkinId = checkinId;
    }
    
    public Passenger getPassenger() {
        return passenger;
    }
    
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
    
    public Flight getFlight() {
        return flight;
    }
    
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    
    public Seat getSeat() {
        return seat;
    }
    
    public void setSeat(Seat seat) {
        this.seat = seat;
    }
    
    public LocalDateTime getCheckinTime() {
        return checkinTime;
    }
    
    public void setCheckinTime(LocalDateTime checkinTime) {
        this.checkinTime = checkinTime;
    }
    
    public Boolean getBoardingPassIssued() {
        return boardingPassIssued;
    }
    
    public void setBoardingPassIssued(Boolean boardingPassIssued) {
        this.boardingPassIssued = boardingPassIssued;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
}