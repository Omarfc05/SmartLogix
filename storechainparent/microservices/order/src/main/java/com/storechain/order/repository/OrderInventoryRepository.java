package com.storechain.order.repository;

import com.storechain.order.entities.OrderInventory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderInventoryRepository extends JpaRepository<OrderInventory, Long>{
    List<OrderInventory> findByCustomerId(Long orderId);

        void deleteByCustomerId(Long orderId);
}
