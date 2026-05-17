package com.storechain.inventory.service;

import com.storechain.inventory.entities.Product;
import com.storechain.inventory.exception.BusinessRuleException;
import com.storechain.inventory.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product create(Product product) {

        repository.findByCode(product.getCode()).ifPresent(p -> {
            throw new BusinessRuleException("1001",
                    HttpStatus.BAD_REQUEST,
                    "Producto ya existe con ese código");
        });

        if (product.getStock() < 0) {
            throw new BusinessRuleException("1002",
                    HttpStatus.BAD_REQUEST,
                    "Stock no puede ser negativo");
        }

        return repository.save(product);
    }

    public Product updateStock(Long id, int quantity) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("1003",
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado"));

        int newStock = product.getStock() + quantity;

        if (newStock < 0) {
            throw new BusinessRuleException("1004",
                    HttpStatus.BAD_REQUEST,
                    "Stock insuficiente");
        }

        product.setStock(newStock);
        return repository.save(product);
    }
    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("1003",
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado"));
    }
    public void delete(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("1003",
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado"));

        repository.delete(product);
    }
}