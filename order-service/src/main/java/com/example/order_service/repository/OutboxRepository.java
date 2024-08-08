package com.example.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.order_service.entity.Outbox;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    
}
