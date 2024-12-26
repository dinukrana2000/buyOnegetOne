package com.buyOnegetOne.gateway_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @GetMapping("/order-service")
    public String orderServiceFallback() {
        return "Order Service is taking too long to respond or is down. Please try again later.";
    }

    @GetMapping("/product-service")
    public String productServiceFallback() {
        return "Product Service is taking too long to respond or is down. Please try again later.";
    }

    @GetMapping("/inventory-service")
    public String inventoryService() {
        return "Inventory Service is taking too long to respond or is down. Please try again later.";
    }
}
