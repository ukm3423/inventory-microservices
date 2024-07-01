package com.masterservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masterservice.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    public Product findByProductNameIgnoreCase(String productName);

    public Product findByProductCodeIgnoreCase(String productCode);
    
    public List<Product> findByCategoryId(Long categoryId);
}
