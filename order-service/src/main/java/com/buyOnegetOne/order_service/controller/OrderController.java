package com.buyOnegetOne.order_service.controller;

import com.buyOnegetOne.order_service.dto.OrderRequest;
import com.buyOnegetOne.order_service.service.OrderService;
import com.buyOnegetOne.order_service.util.EndPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = EndPoint.ORDER_ADD)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    //for the batch processing concept understanding only not related to the project flow
    @GetMapping(value = EndPoint.ORDER_BATCH)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> runBatchJob() {
         return orderService.runBatchJob();
    }

    @GetMapping(value = EndPoint.ORDER_GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getOrder() {
         return orderService.getOrder();
    }
}
