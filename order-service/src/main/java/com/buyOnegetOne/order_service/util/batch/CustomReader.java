package com.buyOnegetOne.order_service.util.batch;

import com.buyOnegetOne.order_service.entity.OrderLineItems;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.stereotype.Component;

@Component
public class CustomReader extends JpaPagingItemReader<OrderLineItems> {

    public CustomReader(EntityManagerFactory entityManagerFactory) {
        this.setEntityManagerFactory(entityManagerFactory);
        this.setQueryString("SELECT o FROM OrderLineItems o");  // Query to fetch order line items
        this.setPageSize(1000);  // Set chunk size
        System.out.println("Reader initialized.");
    }
}
