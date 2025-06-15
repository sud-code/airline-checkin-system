package com.relog.checkin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/seat-selection")
    public String seatSelection() {
        return "seat-selection";
    }

    @GetMapping("/boarding-pass")
    public String boardingPass() {
        return "boarding-pass";
    }
}