package com.eci.orderservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogResponse {
    private Long id;
    private String sku;
    private String name;
    private Double price;
    private Integer availableQuantity;
}

