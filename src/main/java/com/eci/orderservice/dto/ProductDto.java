/*package com.eci.orderservice.dto;

public class ProductDto {
    private Long productId;
    private String sku;
    private String name;
    private String category;
    private Double price;
    private Boolean active;

    // getters & setters
    // or use Lombok @Data if Lombok is available
}
*/

package com.eci.orderservice.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long productId;
    private String sku;
    private Double price;
}

