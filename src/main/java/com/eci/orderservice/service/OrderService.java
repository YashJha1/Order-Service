/*package com.eci.orderservice.service;

import com.eci.orderservice.dto.OrderRequest;
import com.eci.orderservice.dto.PaymentResponse;
import com.eci.orderservice.model.Order;

import java.time.*;
import java.util.List;

public interface OrderService {

    Order createOrder(OrderRequest orderRequest);

    PaymentResponse getOrderPaymentStatus(Long orderId);

    // Additional query methods
    List<Order> getOrdersByCustomerAndStatus(Long customerId, String status);

    List<Order> getOrdersByStatus(String status);

    List<Order> getOrdersByCustomer(Long customerId);

    List<Order> getOrdersByDateRange(OffsetDateTime start, OffsetDateTime end);

    List<Order> getAllOrders();

    Order getOrderById(Long orderId);

    Order updateOrder(Long orderId, Order order);

    void deleteOrder(Long orderId);

    PaymentResponse getPaymentsByOrderId(Long orderId);
}
*/

package com.eci.orderservice.service;

import com.eci.orderservice.dto.OrderRequest;
import com.eci.orderservice.dto.OrderResponse;

public interface OrderService {

    // Create new order
    OrderResponse createOrder(OrderRequest request);

    // Optional helper (for your API GET call)
    //OrderResponse getPaymentsByOrderId(Long orderId);
    Object getPaymentsByOrderId(Long orderId);
}

