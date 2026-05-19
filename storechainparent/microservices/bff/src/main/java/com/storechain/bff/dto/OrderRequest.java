package com.storechain.bff.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String client;
    private List<OrderDetailRequest> details;
}
