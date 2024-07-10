package com.masterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masterservice.models.DeliveryDetails;

public interface DeliveryDetailsRepository extends JpaRepository<DeliveryDetails, Long>{
    
}
