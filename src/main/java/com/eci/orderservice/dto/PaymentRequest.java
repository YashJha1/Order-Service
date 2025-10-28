/*package com.eci.orderservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long orderId;
    private Double amount;
    private String method;
}
*/

package com.eci.orderservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private Long orderId;
    private Double amount;
    private String method;
}

