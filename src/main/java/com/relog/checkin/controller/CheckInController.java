package com.relog.checkin.controller;

import com.relog.checkin.dto.BoardingPassDTO;
import com.relog.checkin.dto.PnrRequest;
import com.relog.checkin.dto.SeatDTO;
import com.relog.checkin.dto.SeatSelectionRequest;
import com.relog.checkin.exception.ResourceNotFoundException;
import com.relog.checkin.exception.SeatAlreadyOccupiedException;
import com.relog.checkin.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/checkin")
public class CheckInController {

    private final CheckInService checkInService;

    @Autowired
    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping("/validate-pnr")
    public ResponseEntity<Map<String, Object>> validatePnr(@RequestBody PnrRequest request) {
        try {
            Long flightId = checkInService.validatePnr(request.getPnr());
            Map<String, Object> response = new HashMap<>();
            response.put("valid", true);
            response.put("flightId", flightId);
            response.put("pnr", request.getPnr());
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("valid", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/seats/{flightId}")
    public ResponseEntity<List<SeatDTO>> getAvailableSeats(@PathVariable Long flightId) {
        try {
            List<SeatDTO> seats = checkInService.getAvailableSeats(flightId);
            return ResponseEntity.ok(seats);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/select-seat")
    public ResponseEntity<?> selectSeat(@RequestBody SeatSelectionRequest request) {
        try {
            SeatDTO selectedSeat = checkInService.selectSeat(request);
            return ResponseEntity.ok(selectedSeat);
        } catch (ResourceNotFoundException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (SeatAlreadyOccupiedException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @GetMapping("/boarding-pass/{pnr}")
    public ResponseEntity<?> generateBoardingPass(@PathVariable String pnr) {
        try {
            BoardingPassDTO boardingPass = checkInService.generateBoardingPass(pnr);
            return ResponseEntity.ok(boardingPass);
        } catch (ResourceNotFoundException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}