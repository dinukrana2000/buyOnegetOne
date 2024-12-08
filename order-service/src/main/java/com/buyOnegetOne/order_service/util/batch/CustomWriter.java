package com.buyOnegetOne.order_service.util.batch;

import com.buyOnegetOne.order_service.dto.OrderLineItemsRequestDto;
import com.buyOnegetOne.order_service.dto.OrderRequest;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomWriter implements ItemWriter<OrderLineItemsRequestDto> {

    private static final List<OrderLineItemsRequestDto> orderRequests = new ArrayList<>();

    @Override
    public void write(Chunk<? extends OrderLineItemsRequestDto> chunk) throws Exception {

        for (OrderLineItemsRequestDto orderLineItemsRequestDto : chunk) {
            System.out.println("Writing OrderDTO: " + orderLineItemsRequestDto.getId());
            orderRequests.add(orderLineItemsRequestDto);  // Add to in-memory list
        }
    }

    // Expose DTO list for further use (e.g., in the service layer)
    public List<OrderLineItemsRequestDto> getOrderDTOList() {
        return new ArrayList<>(orderRequests);
    }

}