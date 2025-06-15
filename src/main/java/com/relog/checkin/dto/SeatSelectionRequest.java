package com.relog.checkin.dto;

public class SeatSelectionRequest {
    private String pnr;
    private Long flightId;
    private Long seatId;
    
    public SeatSelectionRequest() {
    }
    
    public SeatSelectionRequest(String pnr, Long flightId, Long seatId) {
        this.pnr = pnr;
        this.flightId = flightId;
        this.seatId = seatId;
    }
    
    public String getPnr() {
        return pnr;
    }
    
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
    
    public Long getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
    
    public Long getSeatId() {
        return seatId;
    }
    
    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }
}