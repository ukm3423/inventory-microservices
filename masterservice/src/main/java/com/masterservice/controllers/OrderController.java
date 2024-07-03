package com.masterservice.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masterservice.mapper.PlaceOrderRequest;
import com.masterservice.services.OrderService;


@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService; 

    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest req) {


        Object obj = orderService.addOrder(req);

        Map<String, Object> resp = new HashMap<>();
        resp.put("data", obj);

        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

}
