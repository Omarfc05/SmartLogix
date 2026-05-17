package com.storechain.order.controller;

import com.storechain.order.entities.Order;
import com.storechain.order.service.OrderService;
import com.storechain.order.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/v1")
public class OrderRestController {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderRepository repository;

    @GetMapping
    public ResponseEntity<List<Order>> list() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return ResponseEntity.ok(service.create(order));
    }
}