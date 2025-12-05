package com.example.cabbookingmvp.repository;

import com.example.cabbookingmvp.entity.Poi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoiRepository extends JpaRepository<Poi, Long> {
    List<Poi> findByCityIgnoreCase(String city);
}
