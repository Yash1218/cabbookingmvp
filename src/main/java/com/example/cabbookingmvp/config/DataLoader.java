package com.example.cabbookingmvp.config;

import com.example.cabbookingmvp.entity.Driver;
import com.example.cabbookingmvp.repository.DriverRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final DriverRepository driverRepository;

    public DataLoader(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (driverRepository.count() == 0) {
            driverRepository.save(new Driver("Ramesh Pawar", "WagonR", "MH12 AB 3456", 4.8));
            driverRepository.save(new Driver("Anil Patil", "Swift", "MH12 XY 9876", 4.6));
            driverRepository.save(new Driver("Sunil Mane", "Alto", "MH12 CD 1122", 4.7));
        }
    }
}
