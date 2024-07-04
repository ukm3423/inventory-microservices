package com.masterservice.services;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masterservice.dto.OrderDetailsDTO;
import com.masterservice.handlers.Generator;
import com.masterservice.mapper.PlaceOrderRequest;
import com.masterservice.models.Category;
import com.masterservice.models.Order;
import com.masterservice.models.OrderDetails;
import com.masterservice.models.Product;
import com.masterservice.models.Supplier;
import com.masterservice.repository.CategoryRepository;
import com.masterservice.repository.OrderDetailsRepository;
import com.masterservice.repository.OrderRepository;
import com.masterservice.repository.ProductRepository;
import com.masterservice.repository.SupplierRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderDetailsRepository orderDetailsRepo;

    @Autowired
    private Generator generator;

    @Autowired
    private CategoryRepository catRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private SupplierRepository suppRepo;

    public Object addOrder(PlaceOrderRequest req) {
        

        Supplier supplier = suppRepo.findById(req.getSupplierId()).get();
        String orderNumber = Generator.generateOrderNumber(); 
        Order order = Order.builder()
                .orderDate(req.getDate())
                .supplier(supplier)
                .status(0)
                .orderNumber(orderNumber)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        order = orderRepo.save(order);

        List<OrderDetailsDTO> orderList = req.getProductDetailsList(); 

        @SuppressWarnings("rawtypes")
        Iterator itr = orderList.iterator();

        while(itr.hasNext()){
            OrderDetailsDTO od = (OrderDetailsDTO) itr.next();

            Category category = catRepo.findById(od.getCategoryId()).get();
            Product product = productRepo.findById(od.getProductId()).get();

            OrderDetails orderDetails = OrderDetails.builder()
                        .category(category)
                        .product(product)
                        .order(order)
                        .quantity(od.getQuantity())
                        .rate(od.getRate())
                        .status(0)
                        .updatedAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .build();
            orderDetailsRepo.save(orderDetails);
            System.out.println(orderDetails);
        }

        return order;
    }

    public List<Order> getAllOrders() {
        
        return orderRepo.findAll();

    }

    

}
