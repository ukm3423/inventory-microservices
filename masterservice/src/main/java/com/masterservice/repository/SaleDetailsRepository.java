package com.masterservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masterservice.models.Sale;
import com.masterservice.models.SaleDetails;

public interface SaleDetailsRepository extends JpaRepository<SaleDetails, Long>{

    List<SaleDetails> findBySale(Sale sale);
    
}
