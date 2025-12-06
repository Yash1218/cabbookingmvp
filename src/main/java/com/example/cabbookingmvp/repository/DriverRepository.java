package com.example.cabbookingmvp.repository;

import com.example.cabbookingmvp.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
