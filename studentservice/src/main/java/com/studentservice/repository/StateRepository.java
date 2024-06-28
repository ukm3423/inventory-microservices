package com.studentservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.studentservice.models.State;


public interface StateRepository extends JpaRepository<State, Long>{

  

}
