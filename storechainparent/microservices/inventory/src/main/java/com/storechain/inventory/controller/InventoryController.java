package com.storechain.inventory.controller;

import com.storechain.inventory.entities.Product;
import com.storechain.inventory.service.InventoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory/v1")
public class InventoryController {

    @Autowired
    private InventoryService service;


    @GetMapping
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(service.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }


    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product created = service.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(
            @PathVariable("id") Long id,
            @RequestParam("quantity") int quantity) {

        Product updated = service.updateStock(id, quantity);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}