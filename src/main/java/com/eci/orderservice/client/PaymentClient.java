package com.eci.orderservice.client;

import com.eci.orderservice.dto.PaymentRequest;
import com.eci.orderservice.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "payments-service", url = "${payments.service.url}")
public interface PaymentClient {

    @PostMapping("/v1/payments")
    PaymentResponse createPayment(@RequestBody PaymentRequest paymentRequest);

    @GetMapping("/v1/payments/order/{orderId}")
    List<PaymentResponse> getPaymentsByOrderId(@PathVariable("orderId") String orderId);
}

