package com.buyOnegetOne.order_service.mapper;

import com.buyOnegetOne.order_service.dto.OrderLineItemsRequestDto;
import com.buyOnegetOne.order_service.dto.OrderRequest;
import com.buyOnegetOne.order_service.entity.Order;
import com.buyOnegetOne.order_service.entity.OrderLineItems;

import java.util.List;

public class OrderMapper {
    private OrderMapper() {
    }

    public static void DtoToEntity(Order order, OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItems=orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(OrderMapper::maptoDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);
    }

    private static OrderLineItems maptoDto(OrderLineItemsRequestDto orderLineItemsRequestDto){
        OrderLineItems orderLineItems=new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsRequestDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsRequestDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsRequestDto.getSkuCode());
        return orderLineItems;
    }

}
