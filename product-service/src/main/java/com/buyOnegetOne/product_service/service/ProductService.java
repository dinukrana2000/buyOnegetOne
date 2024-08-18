package com.buyOnegetOne.product_service.service;

import com.buyOnegetOne.product_service.dto.ProductRequest;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<Object> addProduct(ProductRequest productRequest);

    ResponseEntity<Object> getAllProducts();
}
