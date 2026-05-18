package com.storechain.shipment.service;

import com.storechain.shipment.entities.Shipment;
import com.storechain.shipment.exception.BusinessRuleException;
import com.storechain.shipment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository repository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Shipment create(Shipment shipment) {

        try {
            // Consultar Order
            Object order = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8084/order/v1/{id}", shipment.getOrderId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            if (order == null) {
                throw new BusinessRuleException("3001",
                        HttpStatus.NOT_FOUND,
                        "Pedido no existe");
            }

            // Validar estado APROBADO
            String status = (String) ((java.util.Map) order).get("status");

            if (!"APROBADO".equals(status)) {
                throw new BusinessRuleException("3002",
                        HttpStatus.BAD_REQUEST,
                        "Pedido no está aprobado");
            }

        } catch (Exception ex) {
            throw new BusinessRuleException("5021",
                    HttpStatus.BAD_GATEWAY,
                    "Error comunicando con Order");
        }

        shipment.setStatus("PENDIENTE");

        return repository.save(shipment);
    }

    public Shipment updateStatus(Long id, String status) {

        Shipment shipment = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("3003",
                        HttpStatus.NOT_FOUND,
                        "Envío no encontrado"));

        shipment.setStatus(status);

        return repository.save(shipment);
    }
}