package com.buyOnegetOne.order_service.service.Impl;

import com.buyOnegetOne.order_service.client.InventoryServiceClient;
import com.buyOnegetOne.order_service.dto.OrderLineItemsRequestDto;
import com.buyOnegetOne.order_service.dto.OrderRequest;
import com.buyOnegetOne.order_service.entity.Order;
import com.buyOnegetOne.order_service.entity.OrderLineItems;
import com.buyOnegetOne.order_service.mapper.OrderMapper;
import com.buyOnegetOne.order_service.repo.OrderLineItemsRepository;
import com.buyOnegetOne.order_service.repo.OrderRepository;
import com.buyOnegetOne.order_service.service.OrderService;
import com.buyOnegetOne.order_service.util.MessageConstant;
import com.buyOnegetOne.order_service.util.batch.CustomWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final MessageSource messageSource;

    private final OrderRepository orderRepository;

    private final JobLauncher jobLauncher;

    private final Job sampleJob;

    private final CustomWriter customWriter;

    private final InventoryServiceClient inventoryServiceClient;

    private final OrderLineItemsRepository orderLineItemsRepository;

    @Override
    @Transactional
    public ResponseEntity<Object> placeOrder(OrderRequest orderRequest) {
        try{

            // Check inventory for each item in the order
            for (OrderLineItemsRequestDto lineItem : orderRequest.getOrderLineItemsDtoList()) {
                ResponseEntity<Object> inventoryResponse = inventoryServiceClient.isInStock(lineItem.getSkuCode(), lineItem.getQuantity());

                // Handle fallback or inventory check failure
                if (inventoryResponse.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                    log.error("Fallback triggered for SKU code: {} and quantity: {}. Inventory service is down.", lineItem.getSkuCode(), lineItem.getQuantity());
                    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(messageSource.getMessage(MessageConstant.SERVICE_UNAVAILABLE,
                            null, null));
                }
                // If any item is not in stock, return an error
                if (inventoryResponse.getStatusCode() != HttpStatus.OK || !(Boolean) inventoryResponse.getBody()) {
                    log.info("Item with SKU code {} and quantity {} is not in stock", lineItem.getSkuCode(), lineItem.getQuantity());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Collections.singletonMap("message",
                                    messageSource.getMessage(MessageConstant.ITEM_NOT_IN_STOCK, new Object[]{lineItem.getSkuCode(), lineItem.getQuantity()}, null)));

                }
            }


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

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Collections.singletonMap("message",
                                messageSource.getMessage(MessageConstant.ORDER_SAVE_SUCCESS, new Object[]{order.getOrderNumber()}, null)));

            }

        }catch (Exception e){
            log.error(String.valueOf(e));
            throw e;
        }
    }

    public ResponseEntity<Object> runBatchJob() {
        try {

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(sampleJob, jobParameters);

            List<OrderLineItemsRequestDto> orderRequests = customWriter.getOrderDTOList();

            log.info("Batch job has been triggered successfully.");
            System.out.println(orderRequests.size());

            return ResponseEntity.status(HttpStatus.OK).body(orderRequests);
        } catch (Exception e) {
            log.error("Failed to trigger batch job: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Failed to trigger batch job.");
        }
    }

    @Override
    public ResponseEntity<Object> getOrder() {
        List<OrderLineItems> orderItems = orderLineItemsRepository.findOrderLineItems();
        return ResponseEntity.status(HttpStatus.OK).body(orderItems);
    }
}
