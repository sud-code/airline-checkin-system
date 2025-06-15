package com.relog.checkin.dto;

import java.time.LocalDateTime;

public class BoardingPassDTO {
    private String passengerName;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String seatNumber;
    private String seatClass;
    private String gate;
    private String boardingTime;
    private String pnr;
    private String barcode;
    
    public BoardingPassDTO() {
    }
    
    public BoardingPassDTO(String passengerName, String flightNumber, String origin, String destination,
                          LocalDateTime departureTime, LocalDateTime arrivalTime, String seatNumber,
                          String seatClass, String gate, String boardingTime, String pnr, String barcode) {
        this.passengerName = passengerName;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.gate = gate;
        this.boardingTime = boardingTime;
        this.pnr = pnr;
        this.barcode = barcode;
    }
    
    // Static builder method to replace @Builder annotation
    public static BoardingPassDTOBuilder builder() {
        return new BoardingPassDTOBuilder();
    }
    
    // Builder class
    public static class BoardingPassDTOBuilder {
        private String passengerName;
        private String flightNumber;
        private String origin;
        private String destination;
        private LocalDateTime departureTime;
        private LocalDateTime arrivalTime;
        private String seatNumber;
        private String seatClass;
        private String gate;
        private String boardingTime;
        private String pnr;
        private String barcode;
        
        public BoardingPassDTOBuilder passengerName(String passengerName) {
            this.passengerName = passengerName;
            return this;
        }
        
        public BoardingPassDTOBuilder flightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }
        
        public BoardingPassDTOBuilder origin(String origin) {
            this.origin = origin;
            return this;
        }
        
        public BoardingPassDTOBuilder destination(String destination) {
            this.destination = destination;
            return this;
        }
        
        public BoardingPassDTOBuilder departureTime(LocalDateTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }
        
        public BoardingPassDTOBuilder arrivalTime(LocalDateTime arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }
        
        public BoardingPassDTOBuilder seatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
            return this;
        }
        
        public BoardingPassDTOBuilder seatClass(String seatClass) {
            this.seatClass = seatClass;
            return this;
        }
        
        public BoardingPassDTOBuilder gate(String gate) {
            this.gate = gate;
            return this;
        }
        
        public BoardingPassDTOBuilder boardingTime(String boardingTime) {
            this.boardingTime = boardingTime;
            return this;
        }
        
        public BoardingPassDTOBuilder pnr(String pnr) {
            this.pnr = pnr;
            return this;
        }
        
        public BoardingPassDTOBuilder barcode(String barcode) {
            this.barcode = barcode;
            return this;
        }
        
        public BoardingPassDTO build() {
            return new BoardingPassDTO(passengerName, flightNumber, origin, destination, departureTime,
                                      arrivalTime, seatNumber, seatClass, gate, boardingTime, pnr, barcode);
        }
    }
    
    // Getters and setters
    public String getPassengerName() {
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }
    
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
    
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
    
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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
    
    public String getGate() {
        return gate;
    }
    
    public void setGate(String gate) {
        this.gate = gate;
    }
    
    public String getBoardingTime() {
        return boardingTime;
    }
    
    public void setBoardingTime(String boardingTime) {
        this.boardingTime = boardingTime;
    }
    
    public String getPnr() {
        return pnr;
    }
    
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
    
    public String getBarcode() {
        return barcode;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}