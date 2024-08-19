package com.buyOnegetOne.order_service.service.Impl;

import com.buyOnegetOne.order_service.dto.OrderRequest;
import com.buyOnegetOne.order_service.entity.Order;
import com.buyOnegetOne.order_service.mapper.OrderMapper;
import com.buyOnegetOne.order_service.repo.OrderRepository;
import com.buyOnegetOne.order_service.service.OrderService;
import com.buyOnegetOne.order_service.util.MessageConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final MessageSource messageSource;

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public ResponseEntity<Object> placeOrder(OrderRequest orderRequest) {
        try{
            Order order=new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            Optional<Order> existOrder=orderRepository.findByOrderNumber(order.getOrderNumber());

            if (existOrder.isPresent()){
                log.info("Order with order number {} already exists",order.getOrderNumber());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(messageSource.getMessage(MessageConstant.ORDER_ALREADY_EXISTS,
                        null,null));
            }
            else {
                OrderMapper.DtoToEntity(order,orderRequest);

                orderRepository.save(order);
                log.info("Order with order number {} is save successfully",order.getOrderNumber());
                return ResponseEntity.status(HttpStatus.CREATED).body(messageSource.getMessage(MessageConstant.ORDER_SAVE_SUCCESS,
                        null,null));
            }

        }catch (Exception e){
            log.error(String.valueOf(e));
            throw e;
        }
    }
}
