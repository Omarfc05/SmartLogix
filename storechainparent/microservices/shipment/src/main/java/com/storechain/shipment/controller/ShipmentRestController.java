package com.storechain.shipment.controller;


import com.storechain.shipment.entities.Shipment;
import com.storechain.shipment.repository.ShipmentRepository;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/shipment/v1")
public class ShipmentRestController {
    
    @Autowired
    ShipmentRepository shipmentRepository;
    
    @GetMapping()
    public List<Shipment> list() {
        return shipmentRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Long id) {
        Optional<Shipment> optionalCustomer = shipmentRepository.findById(id);
        
        if(optionalCustomer.isPresent()) {
            Shipment retorno = optionalCustomer.get();
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Shipment input) {
        return null;
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Shipment input) {
        Shipment retorno = shipmentRepository.save(input);
        return ResponseEntity.ok(retorno);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        shipmentRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
