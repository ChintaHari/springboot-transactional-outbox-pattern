package com.example.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order_service.common.dto.OrderRequestDTO;
import com.example.order_service.entity.Order;
import com.example.order_service.service.OrderService;



@RestController
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        Order order = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(order);
    }
}
