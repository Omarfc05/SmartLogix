package com.storechain.order.service;

import com.storechain.order.entities.*;
import com.storechain.order.exception.BusinessRuleException;
import com.storechain.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Order createOrder(Order order) {

        if (order.getDetails().isEmpty()) {
            throw new BusinessRuleException("2001", HttpStatus.BAD_REQUEST, "Pedido sin productos");
        }

        for (OrderDetail detail : order.getDetails()) {

            if (detail.getQuantity() <= 0) {
                throw new BusinessRuleException("2004", HttpStatus.BAD_REQUEST, "Cantidad inválida");
            }

            detail.setOrder(order);
        }

        order.setStatus("CREADO");

        return orderRepository.save(order);
    }
    public Order updateStatus(Long id, String status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("2005",
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado"));

        if (status.equalsIgnoreCase("VALIDADO")) {

            // validar stock
            for (OrderDetail detail : order.getDetails()) {

                InventoryResponse product = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8086/inventory/v1/{id}", detail.getProductId())
                        .retrieve()
                        .bodyToMono(InventoryResponse.class)
                        .block();

                if (product == null) {
                    throw new BusinessRuleException("2002", HttpStatus.NOT_FOUND, "Producto no existe");
                }

                if (product.getStock() < detail.getQuantity()) {
                    throw new BusinessRuleException("2003", HttpStatus.BAD_REQUEST, "Stock insuficiente");
                }
            }

            order.setStatus("VALIDADO");
        }

        if (status.equalsIgnoreCase("APROBADO")) {

            // descontar stock
            for (OrderDetail detail : order.getDetails()) {

                webClientBuilder.build()
                        .put()
                        .uri("http://localhost:8086/inventory/v1/{id}/stock?quantity=-" + detail.getQuantity(),
                                detail.getProductId())
                        .retrieve()
                        .bodyToMono(Void.class)
                        .block();
            }

            order.setStatus("APROBADO");
        }

        return orderRepository.save(order);
    }
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException(
                        "2005",
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado"
                ));
    }
}