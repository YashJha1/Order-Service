package com.eci.orderservice.client;

import com.eci.orderservice.dto.InventoryRequest;
import com.eci.orderservice.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "${inventory.service.url:http://localhost:8081/v1/inventory}")
public interface InventoryClient {
    @PostMapping("/v1/inventory/reserve")
    InventoryResponse reserveStock(@RequestBody InventoryRequest request);

    @PostMapping("/v1/inventory/release")
    InventoryResponse releaseStock(@RequestBody InventoryRequest request);
}

