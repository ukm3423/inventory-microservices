package com.masterservice.handlers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.masterservice.repository.OrderRepository;

@Component
public class Generator {


    private static OrderRepository orderRepo;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepo) {
        Generator.orderRepo = orderRepo;
    }
    
    public static String generateOrderNumber() {
        String orderNumber;
        do {
            orderNumber = generateRandomOrderNumber();
        } while (!isorderNumberUnique(orderNumber));
        return orderNumber;
    }

    private static String generateRandomOrderNumber() {
        Random random = new Random();
        // You can adjust the length of the random number as needed
        String randomNumber = String.format("%010d", random.nextInt(1000000));
        return "ODR" + randomNumber;
    }

    private static boolean isorderNumberUnique(String orderNumber) {
        return orderRepo.findByOrderNumber(orderNumber) == null;
    }
}
