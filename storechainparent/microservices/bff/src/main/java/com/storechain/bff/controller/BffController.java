package com.storechain.bff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class BffController {
        
    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping("/procesar")
    public Mono<String> procesarCompra(@RequestBody Object carritoData) {
        
        // 1. El BFF le pega primero al Microservicio de Inventario para validar/restar stock
        return webClientBuilder.build()
            .post()
            .uri("http://INVENTORY-SERVICE/api/inventory/validate")
            .bodyValue(carritoData)
            .retrieve()
            .bodyToMono(String.class)
            .flatMap(resultadoStock -> {
                // 2. Si el inventario responde OK, el BFF llama inmediatamente a Pedidos
                return webClientBuilder.build()
                    .post()
                    .uri("http://ORDER-SERVICE/api/orders/create")
                    .bodyValue(carritoData)
                    .retrieve()
                    .bodyToMono(String.class);
            });
    }
}
