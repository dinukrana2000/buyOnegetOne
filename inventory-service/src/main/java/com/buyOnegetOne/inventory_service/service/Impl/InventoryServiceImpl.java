package com.buyOnegetOne.inventory_service.service.Impl;

import com.buyOnegetOne.inventory_service.repo.InventoryRepository;
import com.buyOnegetOne.inventory_service.service.InventoryService;
import com.buyOnegetOne.inventory_service.util.MessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final MessageSource messageSource;
//    @Override
//    @Transactional(readOnly = true)
//    public ResponseEntity<Object> isInStock(String skuCode) {
//       try {
//
////           if (skuCode == null || skuCode.trim().isEmpty()) {
////               return ResponseEntity.status(HttpStatus.BAD_REQUEST).
////                       body(messageSource.getMessage(MessageConstant.SKUCODE_NOT_FOUND, null, null));
////           }
//
//           boolean isInStock = inventoryRepository.findBySkuCode(skuCode).isPresent();
//
//           return ResponseEntity.status(HttpStatus.OK).body(isInStock);
//       }
//       catch (Exception e) {
//           throw new RuntimeException(e);
//       }
//    }
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Object> isInStock(String skuCode, Integer quantity) {
        try {

//           if (skuCode == null || skuCode.trim().isEmpty()) {
//               return ResponseEntity.status(HttpStatus.BAD_REQUEST).
//                       body(messageSource.getMessage(MessageConstant.SKUCODE_NOT_FOUND, null, null));
//           }

            if (skuCode == null || skuCode.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(messageSource.getMessage(MessageConstant.SKUCODE_NOT_FOUND, null, null));
            }

            if (quantity == null || quantity <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(messageSource.getMessage(MessageConstant.QUANTITY_NOT_FOUND, null, null));
            }

            boolean isInStock = inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode,quantity);

            return ResponseEntity.status(HttpStatus.OK).body(isInStock);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
