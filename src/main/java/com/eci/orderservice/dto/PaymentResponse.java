package com.eci.orderservice.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    private Long paymentId;
    private String status;
    private String message;
}

