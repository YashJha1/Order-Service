package com.eci.orderservice.service.impl;

import com.eci.orderservice.client.CatalogClient;
import com.eci.orderservice.client.InventoryClient;
import com.eci.orderservice.client.PaymentClient;
import com.eci.orderservice.dto.*;
import com.eci.orderservice.model.Order;
import com.eci.orderservice.repository.OrderRepository;
import com.eci.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;
    private final CatalogClient catalogClient;
    private final RestTemplate restTemplate; // ✅ added for inter-service calls

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order for customer: {}", request.getCustomerId());

        double totalAmount = 0.0;

        // ✅ Fetch prices from Catalog and compute total
        for (OrderLineDto line : request.getLines()) {
            CatalogResponse catalogProduct = catalogClient.getProductById(line.getProductId());
            double productPrice = catalogProduct.getPrice();
            totalAmount += productPrice * line.getQuantity();
            log.info("Fetched price {} for product {} ({} units)", productPrice, line.getSku(), line.getQuantity());
        }

        // ✅ Check inventory
        InventoryRequest inventoryRequest = InventoryRequest.builder()
                .sku(request.getLines().get(0).getSku())
                .quantity(request.getLines().get(0).getQuantity())
                .warehouse(request.getLines().get(0).getWarehouse())
                .build();

        InventoryResponse inventoryResponse = inventoryClient.reserveStock(inventoryRequest);

        // ✅ Create order in DB
        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .orderStatus("CREATED")
                .paymentStatus("PENDING")
                .orderTotal(totalAmount)
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        // ✅ Process payment via PaymentClient
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getOrderId())
                .amount(totalAmount)
                .method("UPI")
                .build();

        PaymentResponse paymentResponse = paymentClient.processPayment(paymentRequest);

        order.setPaymentStatus(paymentResponse.getStatus());
        order.setOrderStatus(paymentResponse.getStatus().equals("SUCCESS") ? "COMPLETED" : "FAILED");
        orderRepository.save(order);

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .orderTotal(order.getOrderTotal())
                .build();
    }

    // ✅ Correct single version of getPaymentsByOrderId (calls payments microservice)
    @Override
    public Object getPaymentsByOrderId(Long orderId) {
        String url = "http://payments-service:8083/v1/payments?orderId=" + orderId;
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error fetching payments for order {}: {}", orderId, e.getMessage());
            return Map.of("error", "Payment service not reachable or order not found");
        }
    }
}

