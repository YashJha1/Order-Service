/*package com.eci.orderservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private Long customerId;
    private List<OrderLineDto> lines;
}
*/


package com.eci.orderservice.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private Long customerId;
    private List<OrderLineDto> lines;
}

