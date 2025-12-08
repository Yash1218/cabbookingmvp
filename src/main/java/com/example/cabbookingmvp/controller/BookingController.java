package com.example.cabbookingmvp.controller;

import com.example.cabbookingmvp.entity.Ride;
import com.example.cabbookingmvp.repository.RideRepository;
import com.example.cabbookingmvp.repository.DriverRepository;
import com.example.cabbookingmvp.repository.UserRepository;
import com.example.cabbookingmvp.service.RideService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import com.example.cabbookingmvp.service.GeoService;

import javax.servlet.http.HttpSession;


@Controller
public class BookingController {

    @Autowired
    private RideService rideService;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private GeoService geoService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {

        String email = (String) session.getAttribute("email");

        if (email != null) {
            var user = userRepository.findByEmail(email);
            if (user != null) {
                model.addAttribute("username", user.getName());
            }
        }

        return "home";
    }


    // STEP 1 — Process Booking Form (Home Page)
    @PostMapping("/processBooking")
    public String processBooking(@RequestParam String pickup,
                                 @RequestParam String drop,
                                 @RequestParam String vehicle,
                                 Model model) {

        model.addAttribute("pickup", pickup);
        model.addAttribute("drop", drop);
        model.addAttribute("vehicle", vehicle);

        return "finding-driver";
    }


    // STEP 2 — Driver Assigned Page (Now Dynamic Driver)
    @GetMapping("/driver-assigned")
    public String driverAssigned(@RequestParam String pickup,
                                 @RequestParam String drop,
                                 @RequestParam String vehicle,
                                 Model model) {

        var drivers = driverRepository.findAll();
        var driver = drivers.get((int) (Math.random() * drivers.size()));

        model.addAttribute("pickup", pickup);
        model.addAttribute("drop", drop);
        model.addAttribute("vehicle", vehicle);

        model.addAttribute("driverName", driver.getName());
        model.addAttribute("vehicleModel", driver.getVehicleModel());
        model.addAttribute("vehicleNumber", driver.getVehicleNumber());
        model.addAttribute("rating", driver.getRating());

        return "driver-assigned";
    }


    // STEP 3 — Start Ride (Map Page)
    @GetMapping("/startRide")
    public String startRide(@RequestParam String pickup,
                            @RequestParam String drop,
                            @RequestParam String vehicle,
                            @RequestParam String driver,
                            Model model) {

        model.addAttribute("pickup", pickup);
        model.addAttribute("drop", drop);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("driver", driver);

        return "ride";
    }


    // STEP 4 — Finish Ride (Calculate Fare, Save to DB, Show Summary)
    @PostMapping("/finishRide")
    public String finishRide(@RequestParam String pickup,
                             @RequestParam String drop,
                             @RequestParam String vehicle,
                             @RequestParam String driver,
                             Model model) {

        double[] start = geoService.getCoordinates(pickup);
        double[] end   = geoService.getCoordinates(drop);

        double pickupLat = start[0];
        double pickupLon = start[1];
        double dropLat   = end[0];
        double dropLon   = end[1];


        double distance = rideService.calculateDistance(
                pickupLat, pickupLon, dropLat, dropLon
        );

        double fare = rideService.calculateFare(distance, vehicle);

        Ride ride = new Ride();
        ride.setPickup(pickup);
        ride.setDropLocation(drop);
        ride.setDistance(distance);
        ride.setFare(fare);
        ride.setVehicle(vehicle);
        ride.setDriverName(driver);
        ride.setTime(LocalDateTime.now());

        rideRepository.save(ride);

        model.addAttribute("pickup", pickup);
        model.addAttribute("drop", drop);
        model.addAttribute("distance", String.format("%.2f", distance));
        model.addAttribute("fare", String.format("%.2f", fare));
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("driver",driver);


        return "ride-complete";
    }


    // STEP 5 — Ride History
    @GetMapping("/my-rides")
    public String myRides(Model model) {
        model.addAttribute("rides", rideRepository.findAll());
        return "my-rides";
    }
}
