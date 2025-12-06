package com.example.cabbookingmvp.repository;

import com.example.cabbookingmvp.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
}
