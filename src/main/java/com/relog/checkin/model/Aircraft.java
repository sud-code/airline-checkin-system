package com.relog.checkin.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "aircraft")
public class Aircraft {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aircraft_id")
    private Long aircraftId;
    
    @Column(name = "model", nullable = false)
    private String model;
    
    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;
    
    @Column(name = "configuration_type", nullable = false)
    private String configurationType;
    
    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    private List<Seat> seats;
    
    // Constructors
    public Aircraft() {
    }
    
    public Aircraft(Long aircraftId, String model, Integer totalSeats, String configurationType, List<Seat> seats) {
        this.aircraftId = aircraftId;
        this.model = model;
        this.totalSeats = totalSeats;
        this.configurationType = configurationType;
        this.seats = seats;
    }
    
    // Getters and setters
    public Long getAircraftId() {
        return aircraftId;
    }
    
    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public Integer getTotalSeats() {
        return totalSeats;
    }
    
    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }
    
    public String getConfigurationType() {
        return configurationType;
    }
    
    public void setConfigurationType(String configurationType) {
        this.configurationType = configurationType;
    }
    
    public List<Seat> getSeats() {
        return seats;
    }
    
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}