package com.studentservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentservice.models.City;
import com.studentservice.models.State;
import com.studentservice.repository.CityRepository;
import com.studentservice.repository.StateRepository;


@Service
public class CityService {
    
    @Autowired
    private CityRepository cityRepo;

    @Autowired
    private StateRepository stateRepo;


    public List<City> findCityByStateId(Long stateId){
        
        State state = stateRepo.findById(stateId).get(); 
        
        return cityRepo.findByState(state);

    }

    public List<State> getStateList() {
        return stateRepo.findAll();
    }

    public City getCityById(Long cityId) {
        return cityRepo.findById(cityId).get();
    }

    public State getStateById(Long stateId) {
        return stateRepo.findById(stateId).get();
    }

}
