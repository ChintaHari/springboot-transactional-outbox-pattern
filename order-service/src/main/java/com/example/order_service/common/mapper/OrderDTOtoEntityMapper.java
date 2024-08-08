package com.example.order_service.common.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.order_service.common.dto.OrderRequestDTO;
import com.example.order_service.entity.Order;

@Component
public class OrderDTOtoEntityMapper {
    
    public Order mapToEntity(OrderRequestDTO orderRequestDTO) {
        return Order.builder()
                .name(orderRequestDTO.getName())
                .customerId(orderRequestDTO.getCustomerId())
                .productType(orderRequestDTO.getProductType())
                .quantity(orderRequestDTO.getQuantity())
                .price(orderRequestDTO.getPrice())
                .orderDate(new Date())
                .build();
    }
}
