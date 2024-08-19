package com.buyOnegetOne.order_service.service;

import com.buyOnegetOne.order_service.dto.OrderRequest;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<Object> placeOrder(OrderRequest orderRequest);
}
