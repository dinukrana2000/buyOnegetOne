package com.buyOnegetOne.order_service.repo;

import com.buyOnegetOne.order_service.entity.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderLineItemsRepository extends JpaRepository<OrderLineItems, Long> {
    @Query("SELECT o FROM OrderLineItems o")
    List<OrderLineItems> findOrderLineItems();
}
