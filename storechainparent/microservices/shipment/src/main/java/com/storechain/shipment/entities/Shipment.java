package com.storechain.shipment.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Shipment {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private long orderId;
    private String address;
    private String status;
}
