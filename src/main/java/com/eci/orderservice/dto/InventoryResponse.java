/*package com.eci.orderservice.dto;
import lombok.Data;

@Data
public class InventoryResponse {
    private String message;
    // optional success flag if you want
    // getters & setters
}
*/

/*
package com.eci.orderservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    private String sku;
    private boolean available;
}
*/

package com.eci.orderservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {
    private String sku;
    private Integer availableQuantity;
    private Boolean reserved;
    private String message;
}

