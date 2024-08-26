package com.buyOnegetOne.inventory_service.controller;

import com.buyOnegetOne.inventory_service.service.InventoryService;
import com.buyOnegetOne.inventory_service.util.EndPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
//    @GetMapping(EndPoint.IS_INVENTORY)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Object> isInStock(@PathVariable("sku-code") String skuCode) {
//        return inventoryService.isInStock(skuCode);
//    }

      @GetMapping(EndPoint.IS_INVENTORY)
      @ResponseStatus(HttpStatus.OK)
      public ResponseEntity<Object> isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity) {
          return inventoryService.isInStock(skuCode,quantity);
    }
}
