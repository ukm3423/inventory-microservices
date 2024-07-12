package com.masterservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masterservice.repository.SaleDetailsRepository;
import com.masterservice.repository.SaleRepository;

@Service
public class SaleService {
    
    @Autowired
    private SaleRepository saleRepo;

    @Autowired
    private SaleDetailsRepository saleDetailsRepo;


    
}
