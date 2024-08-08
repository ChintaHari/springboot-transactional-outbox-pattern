package com.example.order_service.common.mapper;

import org.springframework.stereotype.Component;

import com.example.order_service.entity.Order;
import com.example.order_service.entity.Outbox;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@Component
public class OrderEntityToOutboxEntityMapper {
    
    @SneakyThrows
    public Outbox mapToEntity(Order order) {
        return Outbox.builder()
                .aggregateId(order.getId().toString())
                .payload(new ObjectMapper().writeValueAsString(order))
                .createdAt(order.getOrderDate())
                .isProcessed(false) 
                .build();            
    }
}
