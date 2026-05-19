package com.storechain.inventory.controller;

import com.storechain.inventory.dto.ProductResponse;
import com.storechain.inventory.entities.Product;
import com.storechain.inventory.repository.ProductRepository;
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

    @Autowired
    private ProductRepository repository;



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

        return ResponseEntity.ok(service.updateStock(id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return repository.findAll().stream().map(p -> {
            ProductResponse dto = new ProductResponse();
            dto.setId(p.getId());
            dto.setTitle(p.getName());
            dto.setDescription("Producto disponible");
            dto.setPrice(1000.0);
            dto.setImageSrc("https://via.placeholder.com/150");
            dto.setStock(p.getStock());
            return dto;
        }).toList();
    }

}