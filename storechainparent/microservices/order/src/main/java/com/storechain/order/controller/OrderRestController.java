package com.storechain.order.controller;


import com.storechain.order.entities.InventoryResponse;
import com.storechain.order.entities.Order;
import com.storechain.order.entities.OrderInventory;
import com.storechain.order.exception.BusinessRuleException;
import com.storechain.order.repository.OrderRepository;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/order/v1")
public class OrderRestController {
    
    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    private WebClient.Builder webClienteBuilder;
    
    @GetMapping()
    public ResponseEntity<List<Order>> list() {
        List<Order> findAll = orderRepository.findAll();
        if (findAll.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(findAll);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) throws BusinessRuleException {
        Optional<Order> optionalCustomer = orderRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            Order retorno = optionalCustomer.get();

            //WebClient webClient = WebClient.create("http://localhost:8083");
            //WebClient webClient = webClienteBuilder.create("http://BUSINESSDOMAIN-PRODUCT");
            List<InventoryResponse> products = new ArrayList<>();
            List<OrderInventory> productName = new ArrayList<>();
            try {
                for (OrderInventory relation : retorno.getOrders()) {

                    InventoryResponse product = webClienteBuilder.build()
                            .get()
                            .uri("http://BUSINESSDOMAIN-PRODUCT/product/{id}", relation.getProductId())
                            .retrieve()
                            .bodyToMono(InventoryResponse.class)
                            .block();

                    if (product != null) {
                        OrderInventory productoRespuesta = new OrderInventory();
                        productoRespuesta.setProductName(product.getClient());
                        productoRespuesta.setProductId(product.getId());
                        productName.add(productoRespuesta);
                        products.add(product);
                    }
                }
            } catch (Exception ex) {
                throw new BusinessRuleException("5020", HttpStatus.MULTI_STATUS.PRECONDITION_FAILED ,"Servicio Caído, contactese con el Admin");
            }
            retorno.setOrders(productName);

            return new ResponseEntity<>(retorno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody Order input) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            Order newOrder = optionalOrder.get();
            newOrder.setOrderNumber(input.getOrderNumber());
            newOrder.setClient(input.getClient());
            newOrder.setStatus(input.getStatus());
            Order retorno = orderRepository.save(newOrder);
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Order input) {

        input.getOrders().forEach(x -> x.setOrder(input));
        Order retorno = orderRepository.save(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
