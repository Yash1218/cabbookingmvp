package com.example.cabbookingmvp.repository;

import com.example.cabbookingmvp.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByVehicleTypeIgnoreCase(String vehicleType);

}
