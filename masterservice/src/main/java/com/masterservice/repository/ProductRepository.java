package com.masterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masterservice.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    public Product findByProductNameIgnoreCase(String productName);

    public Product findByProductCodeIgnoreCase(String productCode);
    
}
