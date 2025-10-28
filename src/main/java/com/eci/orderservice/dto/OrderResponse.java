package com.eci.orderservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private String orderStatus;
    private String paymentStatus;
    private Double orderTotal;
}

