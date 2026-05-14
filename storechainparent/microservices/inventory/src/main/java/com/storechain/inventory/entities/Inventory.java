package com.storechain.inventory.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class Inventory {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private String code;
    private String productName;
    private long stock;
    
    
    
}
