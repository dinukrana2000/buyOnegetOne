package com.buyOnegetOne.product_service.controller;

import com.buyOnegetOne.product_service.dto.ProductRequest;
import com.buyOnegetOne.product_service.service.ProductService;
import com.buyOnegetOne.product_service.util.EndPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = EndPoint.PRODUCT_ADD)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    @GetMapping(value = EndPoint.PRODUCT_GET_ALL)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllProducts(){
        return productService.getAllProducts();
    }
}
