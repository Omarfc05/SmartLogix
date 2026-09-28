package com.storechain.inventory.repository;

import com.storechain.inventory.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //  Magia pura: Configura H2 en memoria automáticamente y levanta JPA
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository; // Aquí SÍ usamos el real, nada de Mocks

    //  PRUEBA 1: Guardar un producto en la base de datos real (H2) y buscarlo
    @Test
    void saveAndFindByCode_ReturnsProduct() {
        // 1. Instanciamos un producto real
        Product product = new Product();
        product.setCode("H2-001");
        product.setTitle("Monitor Gamer");
        product.setDescription("Monitor de prueba H2");
        product.setPrice(150000.0);
        product.setStock(10.0);

        // 2. Ejecución: Lo guardamos de verdad en la base de datos en memoria
        productRepository.save(product);

        // 3. Verificación: Hacemos una consulta SQL (mediante JPA) para buscarlo por su código
        Optional<Product> foundProduct = productRepository.findByCode("H2-001");

        // 4. Aseguramos que la base de datos lo encontró y los datos coinciden
        assertTrue(foundProduct.isPresent(), "El producto debería existir en la base de datos");
        assertEquals("Monitor Gamer", foundProduct.get().getTitle());
        assertEquals(10.0, foundProduct.get().getStock());
    }

    //  PRUEBA 2: Buscar un código que no existe
    @Test
    void findByCode_DoesNotExist_ReturnsEmptyOptional() {
        Optional<Product> foundProduct = productRepository.findByCode("FANTASMA-404");

        // Verificamos que la base de datos responda correctamente que no hay nada
        assertFalse(foundProduct.isPresent());
    }
}