package com.storechain.inventory.service;

import com.storechain.inventory.entities.InventoryMovement;
import com.storechain.inventory.entities.Product;
import com.storechain.inventory.exception.BusinessRuleException;
import com.storechain.inventory.repository.InventoryMovementRepository;
import com.storechain.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InventoryMovementRepository movementRepository; // Necesario porque el servicio guarda un historial

    @InjectMocks
    private InventoryService inventoryService;

    //  PRUEBA 1: Error 1001 - Producto ya existe al crearlo
    @Test
    void create_ProductAlreadyExists_ThrowsException1001() {
        Product newProduct = new Product();
        newProduct.setCode("P100");

        // Simulamos que al buscar el código "P100", la BD ya devuelve un producto
        when(productRepository.findByCode("P100")).thenReturn(Optional.of(new Product()));

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            inventoryService.create(newProduct);
        });

        assertEquals("1001", exception.getCode());
        assertEquals("Producto ya existe con ese código", exception.getMessage());
    }

    //  PRUEBA 2: Error 1004 - Stock insuficiente al hacer una venta (updateStock)
    @Test
    void updateStock_InsufficientStock_ThrowsException1004() {
        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setStock(5.0); // Tiene 5 en stock

        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        // Intentamos restar 10 (quantity negativo simula una venta)
        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            inventoryService.updateStock(1L, -10);
        });

        assertEquals("1004", exception.getCode());
        assertEquals("Stock insuficiente", exception.getMessage());
    }

    //  PRUEBA 3: Camino Feliz - addStockManual guarda el stock y el movimiento
    @Test
    void addStockManual_ValidQuantity_UpdatesStockAndSavesMovement() {
        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setStock(10.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        // Agregamos 20 unidades
        Product result = inventoryService.addStockManual(1L, 20, "Reposición mensual");

        assertEquals(30.0, result.getStock()); // 10 + 20 = 30

        // Verificamos que se haya guardado el producto en la BD 1 vez
        verify(productRepository, times(1)).save(mockProduct);
        // Verificamos que se haya guardado el registro en el historial 1 vez
        verify(movementRepository, times(1)).save(any(InventoryMovement.class));
    }
}