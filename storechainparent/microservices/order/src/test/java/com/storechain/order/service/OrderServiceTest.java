package com.storechain.order.service;

import com.storechain.order.entities.Order;
import com.storechain.order.exception.BusinessRuleException;
import com.storechain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //  Habilita Mockito
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository; // Simulamos la base de datos

    @Mock
    private WebClient.Builder webClientBuilder; // Simulamos el cliente HTTP

    @InjectMocks
    private OrderService orderService; // El servicio real que estamos probando

    //  PRUEBA 1: Verificar el error 2001 (Pedido Vacío)
    @Test
    void createOrder_WithEmptyDetails_ThrowsBusinessRuleException2001() {
        Order emptyOrder = new Order();
        emptyOrder.setDetails(new ArrayList<>()); // Detalles vacíos

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            orderService.createOrder(emptyOrder);
        });

        assertEquals("2001", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Pedido sin productos", exception.getMessage());
    }

    //  PRUEBA 2: Buscar un pedido que SÍ existe
    @Test
    void getById_OrderExists_ReturnsOrder() {
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setOrderNumber("ORD-12345");

        // Le decimos a Mockito: "Cuando el repositorio busque el ID 1, devuelve el mockOrder"
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));

        Order result = orderService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ORD-12345", result.getOrderNumber());
    }

    //  PRUEBA 3: Buscar un pedido que NO existe (Error 2005)
    @Test
    void getById_OrderDoesNotExist_ThrowsBusinessRuleException2005() {
        // Simulamos que la base de datos no encuentra nada
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            orderService.getById(99L);
        });

        assertEquals("2005", exception.getCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    //  PRUEBA 4: Intentar aprobar un pedido que ya está rechazado (Error 2005)
    @Test
    void changeStatus_ApproveNonCreadoOrder_ThrowsBusinessRuleException() {
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setStatus("RECHAZADO"); // El estado inicial NO es CREADO

        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            orderService.changeStatus(1L, "APROBADO");
        });

        assertEquals("2005", exception.getCode());
        assertEquals("Solo pedidos en estado CREADO pueden aprobarse", exception.getMessage());
    }
    //  PRUEBA 5: Rechazar un pedido correctamente
    @Test
    void changeStatus_Rechazado_ChangesStatusSuccessfully() {
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setStatus("CREADO"); // El estado correcto para poder rechazar

        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        // Cuando se guarde, devolvemos la misma orden
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        Order result = orderService.changeStatus(1L, "RECHAZADO");

        assertEquals("RECHAZADO", result.getStatus());
        verify(orderRepository, times(1)).save(mockOrder); // JUnit verifica que se haya llamado al guardado 1 vez
    }
}