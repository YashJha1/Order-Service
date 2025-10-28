/*package com.eci.orderservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long customerId;
    private String orderStatus;
    private String paymentStatus;
    private Double orderTotal;
    private LocalDateTime createdAt;
}
*/

package com.eci.orderservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "orders_order_id_seq"
    )
    @SequenceGenerator(
        name = "orders_order_id_seq",
        sequenceName = "orders_order_id_seq",
        allocationSize = 1
    )
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "order_total")
    private Double orderTotal;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

