package com.masterservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masterservice.models.Order;
import com.masterservice.models.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long>{

    List<OrderDetails> findByOrder(Order order);

}
