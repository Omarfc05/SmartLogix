package com.storechain.inventory.controller;

import com.storechain.inventory.repository.InventoryRepository;
import com.storechain.inventory.entities.Inventory;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


public class InventoryRestController {
    
    @Autowired
    InventoryRepository inventoryRepository;
    
    @GetMapping()
    public List<Inventory> list() {
        return inventoryRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Long id) {
        Optional<Inventory> optionalCustomer = inventoryRepository.findById(id);
        
        if(optionalCustomer.isPresent()) {
            Inventory retorno = optionalCustomer.get();
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Inventory input) {
        return null;
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Inventory input) {
        Inventory retorno=  inventoryRepository.save(input);
        return ResponseEntity.ok(retorno);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        inventoryRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
