package com.eci.orderservice.service.impl;

import com.eci.orderservice.client.PaymentClient;
import com.eci.orderservice.dto.PaymentRequest;
import com.eci.orderservice.dto.PaymentResponse;
import com.eci.orderservice.model.Order;
import com.eci.orderservice.repository.OrderRepository;
import com.eci.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;

    /** Create Order and trigger payment **/
    @Override
    @Transactional
    public Order createOrder(Order order) {
        log.info("Creating new order for Customer ID: {}", order.getCustomerId());

        order.setCreatedAt(OffsetDateTime.now());
        order.setStatus("CREATED");

        // Save order first
        Order savedOrder = orderRepository.save(order);
        log.info("Order saved successfully with ID: {}", savedOrder.getOrderId());

        // Trigger payment
        try {
            BigDecimal amount = order.getOrderTotal() != null ? order.getOrderTotal() : BigDecimal.ZERO;

            PaymentRequest paymentRequest = PaymentRequest.builder()
                    .orderId(savedOrder.getOrderId().toString())
                    .amount(amount)
                    .currency("INR")
                    .paymentMethod("UPI")
                    .idempotencyKey(UUID.randomUUID().toString())
                    .build();

            PaymentResponse paymentResponse = paymentClient.createPayment(paymentRequest);

            if (paymentResponse != null) {
                log.info("Payment triggered for Order ID: {} | Payment ID: {} | Status: {}",
                        savedOrder.getOrderId(),
                        paymentResponse.getPaymentId(),
                        paymentResponse.getStatus());
            } else {
                log.warn("Payment trigger failed for Order ID: {}", savedOrder.getOrderId());
            }

        } catch (Exception e) {
            log.error("Payment service call failed for Order ID: {} | Reason: {}", savedOrder.getOrderId(), e.getMessage());
        }

        return savedOrder;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(existing -> {
                    existing.setStatus(updatedOrder.getStatus());
                    existing.setOrderTotal(updatedOrder.getOrderTotal());
                    log.info("Updating Order ID: {} | New Status: {}", id, updatedOrder.getStatus());
                    return orderRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        log.info("Deleting order with ID: {}", id);
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> getOrdersByCustomerAndStatus(Long customerId, String status) {
        return orderRepository.findByCustomerIdAndStatus(customerId, status);
    }

    @Override
    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> getOrdersByDateRange(OffsetDateTime start, OffsetDateTime end) {
        return orderRepository.findByCreatedAtBetween(start, end);
    }

    @Override
    public List<PaymentResponse> getPaymentsByOrderId(Long orderId) {
        return paymentClient.getPaymentsByOrderId(String.valueOf(orderId));
    }
}

