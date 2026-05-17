package com.storechain.order.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}