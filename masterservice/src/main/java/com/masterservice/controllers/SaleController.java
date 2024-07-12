package com.masterservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masterservice.mapper.PlaceOrderRequest;
import com.masterservice.models.Order;
import com.masterservice.services.OrderService;

@RestController
@CrossOrigin
@RequestMapping("/sale")
public class SaleController {
    /**
     * * ===========================================================================
     * * ======================== Module : SaleController ======================
     * * ======================== Created By : Umesh Kumar =========================
     * * ======================== Created On : 17-07-2024 ==========================
     * * ===========================================================================
     * * | Code Status : On
     */

     @Autowired
    private OrderService orderService;

    /*
     * Place a New Order 
     */
    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest req) {

        Object obj = orderService.addOrder(req);

        Map<String, Object> resp = new HashMap<>();
        resp.put("data", obj);

        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @GetMapping("/get-order-list")
    public ResponseEntity<?> getOrderList(){
        
        List<Order> orderList = orderService.getAllOrders();

        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }


}
