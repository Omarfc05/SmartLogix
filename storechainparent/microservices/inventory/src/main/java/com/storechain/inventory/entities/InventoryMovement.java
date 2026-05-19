package com.storechain.inventory.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "inventory_movements")
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // IN para ingresos, OUT para salidas
    private String movementType;

    private Integer quantity;
    private LocalDateTime createdAt;
    private String reason; // Ej: "Reposición manual", "Orden de compra ORD-123"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}