package com.buyOnegetOne.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "http://localhost:8089/api/inventory")
public interface InventoryServiceClient {
    @GetMapping("/")
    ResponseEntity<Object> isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);
}
