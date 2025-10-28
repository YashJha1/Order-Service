/*package com.eci.orderservice.client;

import com.eci.orderservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service", url = "${catalog.service.url:http://catalog-service:8080/v1/products}")
public interface CatalogClient {
    @GetMapping("/v1/products/sku/{sku}")
    ProductDto getProductBySku(@PathVariable("sku") String sku);
}
*/

package com.eci.orderservice.client;

import com.eci.orderservice.dto.CatalogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service", url = "http://localhost:8081/v1/catalog")
public interface CatalogClient {

    @GetMapping("/products/{productId}")
    CatalogResponse getProductById(@PathVariable("productId") Long productId);
}

