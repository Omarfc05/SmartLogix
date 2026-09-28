package com.storechain.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storechain.inventory.entities.Product;
import com.storechain.inventory.repository.ProductRepository;
import com.storechain.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;

    // Tu controlador usa el repositorio directamente en el getAll(), así que debemos mockearlo también
    @MockitoBean
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    //  PRUEBA 1: Crear un producto (POST /inventory/v1)
    @Test
    void createProduct_Returns201Created() throws Exception {
        Product inputProduct = new Product();
        inputProduct.setCode("P200");
        inputProduct.setTitle("Laptop X");
        inputProduct.setStock(10.0);


        inputProduct.setDescription("Laptop de prueba para test");
        inputProduct.setPrice(500000.0);
        inputProduct.setImageSrc("http://imagen-de-prueba.png");

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setCode("P200");
        savedProduct.setTitle("Laptop X");
        savedProduct.setDescription("Laptop de prueba para test");
        savedProduct.setPrice(500000.0);
        savedProduct.setImageSrc("http://imagen-de-prueba.png");

        // Simulamos que el servicio crea y devuelve el producto con ID 1
        when(inventoryService.create(any(Product.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/inventory/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputProduct))) // Convierte el objeto a JSON
                .andExpect(status().isCreated()) // 201 CREATED
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.code").value("P200"));
    }

    //  PRUEBA 2: Reponer Stock Manual (POST /inventory/v1/{id}/stock/replenish)
    @Test
    void replenishStock_Returns200Ok() throws Exception {
        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setStock(50.0);

        // Simulamos el servicio pasando los parámetros exactos (ID 1, 20 unidades, y la razón)
        when(inventoryService.addStockManual(eq(1L), eq(20), eq("Llegada camion"))).thenReturn(updatedProduct);

        mockMvc.perform(post("/inventory/v1/1/stock/replenish")
                        .param("quantity", "20")
                        .param("reason", "Llegada camion"))
                .andExpect(status().isOk()) // 200 OK
                .andExpect(jsonPath("$.stock").value(50.0));
    }

    //  PRUEBA 3: Listar todos los productos (GET /inventory/v1)
    @Test
    void getAllProducts_ReturnsListAnd200Ok() throws Exception {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setTitle("Mouse");
        p1.setStock(15.0);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setTitle("Teclado");
        p2.setStock(5.0);

        // Simulamos el findAll() del repositorio, ya que el controlador lo llama directo
        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/inventory/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // Debería haber 2 elementos en la lista
                .andExpect(jsonPath("$[0].title").value("Mouse"))
                .andExpect(jsonPath("$[1].title").value("Teclado"));
    }
}