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

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }

    public Order createOrder(Order order) {

        if (order.getDetails().isEmpty()) {
            throw new BusinessRuleException("2001", HttpStatus.BAD_REQUEST, "Pedido sin productos");
        }

        for (OrderDetail detail : order.getDetails()) {

            if (detail.getQuantity() <= 0) {
                throw new BusinessRuleException("2004", HttpStatus.BAD_REQUEST, "Cantidad inválida");
            }

            detail.setOrder(order);
            order.setOrderNumber(generateOrderNumber());
        }

        order.setStatus("CREADO");

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
    public Order changeStatus(Long id, String newStatus) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException(
                        "2004",
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado"
                ));

        switch (newStatus.toUpperCase()) {

            case "APROBADO":

                if (!order.getStatus().equals("CREADO")) {
                    throw new BusinessRuleException(
                            "2005",
                            HttpStatus.BAD_REQUEST,
                            "Solo pedidos en estado CREADO pueden aprobarse"
                    );
                }

                order.setStatus("APROBADO");
                break;

            case "RECHAZADO":

                if (!order.getStatus().equals("CREADO")) {
                    throw new BusinessRuleException(
                            "2006",
                            HttpStatus.BAD_REQUEST,
                            "Solo pedidos en estado CREADO pueden rechazarse"
                    );
                }

                order.setStatus("RECHAZADO");
                break;

            default:
                throw new BusinessRuleException(
                        "2007",
                        HttpStatus.BAD_REQUEST,
                        "Estado inválido"
                );
        }

        return orderRepository.save(order);
    }
}