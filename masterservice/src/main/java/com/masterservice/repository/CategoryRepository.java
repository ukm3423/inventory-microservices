package com.masterservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.masterservice.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    public Category findByCategoryNameIgnoreCase(String categoryName);
    
}
