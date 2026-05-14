package com.storechain.shipment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ShipmentOrder {
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private long OrderId;
    private String status;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Shipment.class)
    @JoinColumn(name = "orderId",nullable = true)
    private Shipment shipment;
}
