package com.masterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masterservice.models.SaleDetails;

public interface SaleDetailsRepository extends JpaRepository<SaleDetails, Long>{
    
}
