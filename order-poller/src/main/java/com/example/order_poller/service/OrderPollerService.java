package com.example.order_poller.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.order_poller.common.MessagePublisher;
import com.example.order_poller.entity.Outbox;
import com.example.order_poller.repository.OutboxRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@EnableScheduling
@Slf4j
public class OrderPollerService {
    
    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private MessagePublisher messagePublisher;

    @Scheduled(fixedRate = 30000)
    public void pollOutboxAndPublishToKafka() {
        List<Outbox> unprocessedRecords =  outboxRepository.findByIsProcessedFalse();
        log.info("Found {} unprocessed records in outbox", unprocessedRecords.size());
           
        unprocessedRecords.forEach(outbox -> {
            try{
                messagePublisher.publish(outbox.getPayload());
                outbox.setProcessed(true);
                outboxRepository.save(outbox);
            } catch (Exception e) {
                log.error("Unable to send message [" 
                + outbox.getPayload() + "] to Kafka due to: " 
                + e.getMessage());
            }
        });
    }
}
