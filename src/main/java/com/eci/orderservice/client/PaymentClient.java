package com.eci.orderservice.client;

import com.eci.orderservice.dto.PaymentRequest;
import com.eci.orderservice.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "payment-service", url = "http://payments-service:8083/v1/payments")
public interface PaymentClient {

    @PostMapping("/v1/payments")
    PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest);

    @GetMapping("/v1/payments/{orderId}")
    PaymentResponse getPaymentByOrderId(@PathVariable("orderId") Long orderId);
}

