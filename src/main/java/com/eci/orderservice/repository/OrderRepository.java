package com.eci.orderservice.repository;

import com.eci.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.*;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByOrderStatus(String orderStatus);

    List<Order> findByCustomerIdAndOrderStatus(Long customerId, String orderStatus);
    
    // ✅ Fetch orders within a date range
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :start AND :end")
    List<Order> findOrdersByDateRange(@Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end);


    //List<Order> findByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end);
}

