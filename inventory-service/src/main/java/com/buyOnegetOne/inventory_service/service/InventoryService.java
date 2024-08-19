package com.buyOnegetOne.inventory_service.service;

import org.springframework.http.ResponseEntity;

public interface InventoryService {
    ResponseEntity<Object> isInStock(String skuCode);
}
