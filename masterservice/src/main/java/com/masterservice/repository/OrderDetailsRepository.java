package com.masterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masterservice.models.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long>{

}
