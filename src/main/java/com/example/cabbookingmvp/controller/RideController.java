package com.example.cabbookingmvp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RideController {

    @PostMapping("/ride")
    public String processRide(@RequestParam String pickup,
                              @RequestParam String drop,
                              Model model) {

        model.addAttribute("pickup", pickup);
        model.addAttribute("drop", drop);

        return "ride";
    }
}
