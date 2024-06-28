package com.studentservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentservice.models.City;
import com.studentservice.models.State;


public interface CityRepository extends JpaRepository<City, Long> {
    
    List<City> findByState(State state);

}
