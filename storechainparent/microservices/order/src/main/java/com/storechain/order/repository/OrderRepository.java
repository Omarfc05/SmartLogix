package com.storechain.order.repository;

import com.storechain.order.entities.Order;
import com.storechain.order.entities.OrderInventory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
