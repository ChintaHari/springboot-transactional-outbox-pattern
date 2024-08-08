package com.example.order_poller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.order_poller.entity.Outbox;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    
    List<Outbox> findByIsProcessedFalse();
}
