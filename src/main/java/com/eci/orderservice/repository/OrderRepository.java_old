/*package com.eci.orderservice.repository;

import com.eci.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	

}
*/

/*package com.eci.orderservice.repository;

import com.eci.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    //  Find by status
    List<Order> findByOrderStatus(String orderStatus);

    //  Find by customerId
    List<Order> findByCustomerId(Long customerId);

    //  Find by customerId and orderstatus
    List<Order> findByCustomerIdAndOrderStatus(Long customerId, String orderStatus);

    // Optional date range filtering
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :start AND :end")
    List<Order> findByCreatedAtBetween(@Param("start") OffsetDateTime start, @Param("end") OffsetDateTime end);
}
*/

package com.eci.orderservice.repository;

import com.eci.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    //  Get all orders by status
    List<Order> findByStatus(String status);

    //  Get all orders for a customer
    List<Order> findByCustomerId(Long customerId);

    //  Get all orders for a customer with a specific status
    List<Order> findByCustomerIdAndStatus(Long customerId, String status);

    // Get orders created within a date range
    List<Order> findByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end);
}

