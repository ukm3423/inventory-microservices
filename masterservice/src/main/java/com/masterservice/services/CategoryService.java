package com.masterservice.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.masterservice.dto.CategoryDTO;
import com.masterservice.models.Category;
import com.masterservice.repository.CategoryRepository;

import jakarta.validation.Valid;

@Service
public class CategoryService {
    

    @Autowired
    private CategoryRepository  categoryRepo;

    /*
     * *********** Adding a new category ********
     * 
     * @param req
     * @return
     */
    public Category saveCategory(CategoryDTO req) {
        Category category = Category.builder()
                .categoryName(req.getCategoryName())
                .status(1)
                .categoryDescription(req.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

                categoryRepo.save(category);
        return category;
    }
    

    public Category searchByCategoryName(String categoryName) {
        return categoryRepo.findByCategoryNameIgnoreCase(categoryName);
    }


    public Category updateCategoryById(Long categoryId, @Valid CategoryDTO req) {
        
        Category category = null;
        Optional<Category> op = categoryRepo.findById(categoryId);
        if (op.isPresent()) {
            category = op.get();
            category.setCategoryName(req.getCategoryName());
            category.setCategoryDescription(req.getDescription());
            category.setUpdatedAt(LocalDateTime.now());

            categoryRepo.save(category);
        }
        return category;

    }

    public Boolean deleteCategoryById(Long id) {
        
        Optional<Category> op = categoryRepo.findById(id);

        if (op.isPresent()) {
            Category category = op.get();
            if(category.getStatus() == 0){
                category.setStatus(1);
            }else{
                category.setStatus(0);
            }
            
            categoryRepo.save(category);
            return true;
        } else {
            return false;
        }
    }


    public Category getCategoryById(Long categoryId) {
        return categoryRepo.findById(categoryId).get();
    }


    public Page<Category> getCategoryList(Pageable pageable) {
        return categoryRepo.findAll(pageable);
    }

    public List<Category> getCoursesList() {
        // Creating a Sort object to specify the sorting criteria
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
    
        // Returning the list of courses sorted by descending order of ID
        return categoryRepo.findAll(sort);
    }


}
