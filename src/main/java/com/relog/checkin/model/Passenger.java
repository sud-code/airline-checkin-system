package com.relog.checkin.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "passengers")
public class Passenger {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Long passengerId;
    
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @OneToMany(mappedBy = "passenger")
    private List<CheckIn> checkIns;
    
    // Constructors
    public Passenger() {
    }
    
    public Passenger(Long passengerId, Booking booking, String firstName, String lastName, 
                    String email, String phone, List<CheckIn> checkIns) {
        this.passengerId = passengerId;
        this.booking = booking;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.checkIns = checkIns;
    }
    
    // Getters and setters
    public Long getPassengerId() {
        return passengerId;
    }
    
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }
    
    public Booking getBooking() {
        return booking;
    }
    
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public List<CheckIn> getCheckIns() {
        return checkIns;
    }
    
    public void setCheckIns(List<CheckIn> checkIns) {
        this.checkIns = checkIns;
    }
}