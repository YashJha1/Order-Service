/*package com.eci.orderservice.dto;

import lombok.Data;

@Data
public class OrderLineDto {
    private String sku;
    private Long productId;
    private Integer quantity;
    private String warehouse; // ✅ Added
}
*/

package com.eci.orderservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineDto {
    private String sku;
    private Long productId;
    private int quantity;
    private String warehouse;
}

