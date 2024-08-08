package com.example.order_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.order_service.common.dto.OrderRequestDTO;
import com.example.order_service.common.mapper.OrderDTOtoEntityMapper;
import com.example.order_service.common.mapper.OrderEntityToOutboxEntityMapper;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.Outbox;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.repository.OutboxRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private OrderDTOtoEntityMapper orderDTOtoEntityMapper;

    @Autowired
    private OrderEntityToOutboxEntityMapper orderEntityToOutboxEntityMapper;
    
    @Transactional
    public Order createOrder(OrderRequestDTO orderRequestDTO) {
        Order order = orderDTOtoEntityMapper.mapToEntity(orderRequestDTO);
        order = orderRepository.save(order);

        Outbox outbox = orderEntityToOutboxEntityMapper.mapToEntity(order);
        outboxRepository.save(outbox);

        return order;
    }
}
