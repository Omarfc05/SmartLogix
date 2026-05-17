package com.storechain.order.service;

import com.storechain.order.entities.Order;
import com.storechain.order.entities.OrderDetail;
import com.storechain.order.exception.BusinessRuleException;
import com.storechain.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public Order create(Order order) {

        if (order.getDetails() == null || order.getDetails().isEmpty()) {
            throw new BusinessRuleException("2001",
                    HttpStatus.BAD_REQUEST,
                    "El pedido no puede estar vacío");
        }


        for (OrderDetail detail : order.getDetails()) {
            if (detail.getQuantity() <= 0) {
                throw new BusinessRuleException("2002",
                        HttpStatus.BAD_REQUEST,
                        "Cantidad inválida");
            }
        }

        // Estado inicial
        order.setStatus("CREADO");

        // Relación bidireccional
        order.getDetails().forEach(d -> d.setOrder(order));

        return repository.save(order);
    }

    public Order getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("2003",
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado"));
    }
}