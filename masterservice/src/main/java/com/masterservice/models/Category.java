package com.masterservice.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="category_name", nullable=false)
    private String categoryName;

    @Column(name="category_desc", nullable=true)
    private String categoryDescription;

    @Column(name="status" , columnDefinition = "integer default 0")
    private Integer status;

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime createdAt; 

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    
}
