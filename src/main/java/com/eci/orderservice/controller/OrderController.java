package com.eci.orderservice.controller;

import com.eci.orderservice.dto.PaymentResponse;
import com.eci.orderservice.model.Order;
import com.eci.orderservice.service.OrderService;
import com.eci.orderservice.client.PaymentClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final PaymentClient paymentClient;

    /** GET all or filtered orders **/
    @GetMapping
    public List<Order> getOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        if (status != null && customerId != null) {
            return orderService.getOrdersByCustomerAndStatus(customerId, status);
        } else if (status != null) {
            return orderService.getOrdersByStatus(status);
        } else if (customerId != null) {
            return orderService.getOrdersByCustomer(customerId);
        } else if (startDate != null && endDate != null) {
            OffsetDateTime start = OffsetDateTime.parse(startDate);
            OffsetDateTime end = OffsetDateTime.parse(endDate);
            return orderService.getOrdersByDateRange(start, end);
        } else {
            return orderService.getAllOrders();
        }
    }

    /** Get order by ID **/
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Create new order **/
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order savedOrder = orderService.createOrder(order);
            log.info("Order created successfully with ID: {}", savedOrder.getOrderId());
            return ResponseEntity
                    .created(URI.create("/v1/orders/" + savedOrder.getOrderId()))
                    .body(savedOrder);
        } catch (Exception e) {
            log.error("Failed to create order: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /** Update order **/
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        try {
            Order order = orderService.updateOrder(id, updatedOrder);
            log.info("Order updated successfully for ID: {}", id);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            log.error("Order not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Failed to update order: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /** Delete order **/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            log.info("Order deleted successfully for ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Failed to delete order: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /** Get payments for order **/
    @GetMapping("/{orderId}/payments")
    public ResponseEntity<?> getPaymentsForOrder(@PathVariable Long orderId) {
        List<PaymentResponse> payments = orderService.getPaymentsByOrderId(orderId);
        if (payments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No payment found for order " + orderId));
        }
        return ResponseEntity.ok(payments);
    }
}

