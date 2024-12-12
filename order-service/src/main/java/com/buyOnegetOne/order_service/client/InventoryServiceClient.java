package com.buyOnegetOne.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${inventory.service.name}")
public interface InventoryServiceClient {
    @GetMapping("/api/inventory/")
    ResponseEntity<Object> isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);
}
