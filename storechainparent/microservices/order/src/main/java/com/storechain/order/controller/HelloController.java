package com.storechain.order.controller;

import com.storechain.order.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello/v1")
@CrossOrigin(origins = "*")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/send")
    public ResponseEntity<String> send(@RequestParam("msg") String msg) {
        helloService.sendMessage(msg);
        return ResponseEntity.ok("Mensaje enviado a la cola: " + msg);
    }
}