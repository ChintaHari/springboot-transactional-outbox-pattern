package com.example.order_service.common.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {
     private String name;
    private String customerId;
    private String productType;
    private int quantity;
    private BigDecimal price;
    
}
