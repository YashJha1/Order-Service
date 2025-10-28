package com.eci.orderservice.controller;
import com.eci.orderservice.dto.OrderRequest;
import com.eci.orderservice.dto.OrderResponse;
import com.eci.orderservice.model.Order;
import com.eci.orderservice.repository.OrderRepository;
import com.eci.orderservice.service.OrderService;  // ✅ import this
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService; // ✅ Add this dependency

    // ✅ 1. Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ✅ 2. Get order by ID
    @GetMapping("/{orderId}")
    public Optional<Order> getOrderById(@PathVariable Long orderId) {
        return orderRepository.findById(orderId);
    }

    // ✅ 3. Delete order by ID
    @DeleteMapping("/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return "Order ID " + orderId + " deleted successfully.";
        } else {
            return "Order ID " + orderId + " not found.";
        }
    }

    // ✅ 4. Get Payments for a specific Order
    @GetMapping("/{orderId}/payments")
    public Object getPaymentsForOrder(@PathVariable Long orderId) {
        return orderService.getPaymentsByOrderId(orderId);
    }

     // ✅ 5. Create a new order
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    //@PostMapping
    //public OrderResponse createOrder(@RequestBody OrderRequest request)


    // ✅ 6. Get orders by status (e.g. COMPLETED, FAILED, PENDING)
    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable String status) {
    	return orderRepository.findByOrderStatus(status.toUpperCase());
    }

// ✅ 7. Get orders within a date range
    @GetMapping("/daterange")
    public List<Order> getOrdersByDateRange(@RequestParam String start,
                                        @RequestParam String end) {
    	LocalDateTime startDate = LocalDateTime.parse(start);
    	LocalDateTime endDate = LocalDateTime.parse(end);
    	return orderRepository.findOrdersByDateRange(startDate, endDate);
    }
    
    // ✅ 8. Get all orders for a specific customer
    @GetMapping("/customer/{customerId}")
    public List<Order> getOrdersByCustomer(@PathVariable Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // ✅ 9. Get all orders for a specific customer AND status
    @GetMapping("/customer/{customerId}/status/{status}")
    public List<Order> getOrdersByCustomerAndStatus(@PathVariable Long customerId,
                                                    @PathVariable String status) {
        return orderRepository.findByCustomerIdAndOrderStatus(customerId, status.toUpperCase());
    }
}

