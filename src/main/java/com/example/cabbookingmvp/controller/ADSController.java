package com.example.cabbookingmvp.controller;

import com.example.cabbookingmvp.entity.Poi;
import com.example.cabbookingmvp.repository.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class ADSController {

    @Autowired
    private PoiRepository poiRepository;

    @GetMapping("/suggestions")
    public List<Poi> getSuggestions(@RequestParam String city) {
        return poiRepository.findByCityIgnoreCase(city);
    }
}
