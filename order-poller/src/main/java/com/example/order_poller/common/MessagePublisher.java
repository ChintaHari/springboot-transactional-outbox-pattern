package com.example.order_poller.common;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String payload) {
        CompletableFuture<SendResult<String, String>> completableFuture = 
                kafkaTemplate.send("outbox-events", payload);

        completableFuture.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent Message [" + payload + "] to Kafka with offset [" 
                + result.getRecordMetadata().offset() + " and partition [" 
                + result.getRecordMetadata().partition() + "]");
            } else {
                System.out.println("Unable to send message [" 
                + payload + "] to Kafka due to: " 
                + ex.getMessage());
            }
        });
    }
    
}
