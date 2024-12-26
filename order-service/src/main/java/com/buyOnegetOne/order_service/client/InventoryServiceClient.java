package com.buyOnegetOne.order_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${inventory.service.name}")
public interface InventoryServiceClient {
    @GetMapping("/api/inventory/")
    @CircuitBreaker(name = "inventory-service", fallbackMethod = "isInStockFallback")
    @Retry(name = "inventory-service", fallbackMethod = "isInStockFallback")
    ResponseEntity<Object> isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);

    default ResponseEntity<Object> isInStockFallback(String skuCode, Integer quantity, Throwable t) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(false);
    }

}
