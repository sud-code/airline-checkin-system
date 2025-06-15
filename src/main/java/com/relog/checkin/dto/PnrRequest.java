package com.relog.checkin.dto;

public class PnrRequest {
    private String pnr;
    
    public PnrRequest() {
    }
    
    public PnrRequest(String pnr) {
        this.pnr = pnr;
    }
    
    public String getPnr() {
        return pnr;
    }
    
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
}