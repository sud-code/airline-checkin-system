package com.relog.checkin.dto;

public class SeatDTO {
    private Long id;
    private String seatNumber;
    private String seatClass;
    private String seatType;
    private boolean available;
    
    public SeatDTO() {
    }
    
    public SeatDTO(Long id, String seatNumber, String seatClass, String seatType, boolean available) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.seatType = seatType;
        this.available = available;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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