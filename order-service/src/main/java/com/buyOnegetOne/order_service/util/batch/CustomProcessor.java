package com.buyOnegetOne.order_service.util.batch;

import com.buyOnegetOne.order_service.dto.OrderLineItemsRequestDto;
import com.buyOnegetOne.order_service.dto.OrderRequest;
import com.buyOnegetOne.order_service.entity.Order;
import com.buyOnegetOne.order_service.entity.OrderLineItems;
import org.springframework.batch.item.ItemProcessor;

import java.util.stream.Collectors;

public class CustomProcessor implements ItemProcessor<OrderLineItems, OrderLineItemsRequestDto> {
    @Override
    public OrderLineItemsRequestDto process(OrderLineItems orderLineItems) throws Exception {
        OrderLineItemsRequestDto orderLineItemsRequestDto = new OrderLineItemsRequestDto();

        // Convert each OrderLineItems to OrderLineItemDTO
        orderLineItemsRequestDto.setId(orderLineItems.getId());
        orderLineItemsRequestDto.setSkuCode(orderLineItems.getSkuCode());
        orderLineItemsRequestDto.setQuantity(orderLineItems.getQuantity());
        orderLineItemsRequestDto.setPrice(orderLineItems.getPrice());

        System.out.println("Mapped Order to OrderDTO: " +  orderLineItems.getId());

        return orderLineItemsRequestDto;
    }


}
