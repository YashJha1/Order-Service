/*package com.eci.orderservice.dto;

import lombok.Data;

@Data
public class InventoryRequest {
    private Long productId;
    private String warehouse;
    private Integer quantity;
}
*/
/*
package com.eci.orderservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequest {
    private String sku;
    private int quantity;
}
*/


package com.eci.orderservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequest {
    private String sku;
    private Integer quantity;
    private String warehouse;  // ✅ add this field (was missing)
}

