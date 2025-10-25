package com.eci.orderservice.service;

import com.eci.orderservice.dto.PaymentResponse;
import com.eci.orderservice.model.Order;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    // Basic CRUD
    List<Order> getAllOrders();

    Optional<Order> getOrderById(Long orderId);

    Order createOrder(Order order);

    Order updateOrder(Long orderId, Order updatedOrder);

    void deleteOrder(Long orderId);

    // Filters
    List<Order> getOrdersByStatus(String status);

    List<Order> getOrdersByCustomer(Long customerId);

    List<Order> getOrdersByCustomerAndStatus(Long customerId, String status);

    List<Order> getOrdersByDateRange(OffsetDateTime startDate, OffsetDateTime endDate);

    // Payment integration
    List<PaymentResponse> getPaymentsByOrderId(Long orderId);
}

