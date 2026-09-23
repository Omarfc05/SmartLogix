package com.storechain.order.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Productor: Envía a la cola 'hello'
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("hello", message);
        System.out.println(" Send: " + message);
    }

    // Consumidor: Escucha la cola 'hello'
    @RabbitListener(queues = "hello")
    public void receiveMessage(String message) {
        System.out.println(" Received: " + message);
    }
}