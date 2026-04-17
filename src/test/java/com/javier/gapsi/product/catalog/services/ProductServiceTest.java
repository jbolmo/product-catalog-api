package com.javier.gapsi.product.catalog.services;

import com.javier.gapsi.product.catalog.dtos.ProductResponse;
import com.javier.gapsi.product.catalog.dtos.ProductUpdateRequest;
import com.javier.gapsi.product.catalog.entities.Product;
import com.javier.gapsi.product.catalog.exceptions.ProductNotFoundException;
import com.javier.gapsi.product.catalog.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService")
class ProductServiceTest {

    private static final String PRODUCT_ID = "ART1234567";

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("getProduct - product exists - returns mapped ProductResponse")
    void getProductWhenProductExistsThenReturnProductResponse() {
        // Given
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(buildProduct()));

        // When
        ProductResponse response = productService.getProduct(PRODUCT_ID);

        // Then
        assertEquals(PRODUCT_ID, response.id());
        assertEquals("Smartphone", response.nombre());
        assertEquals("Gama alta 128GB", response.descripcion());
        assertEquals(new BigDecimal("599.99"), response.precio());
        assertEquals("X-2024", response.modelo());
    }

    @Test
    @DisplayName("getProduct - product not found - throws ProductNotFoundException")
    void getProductWhenProductNotFoundThenThrowException() {
        // Given
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(PRODUCT_ID));
    }

    @Test
    @DisplayName("updateProduct - product exists with full request - saves and returns updated response")
    void updateProductWhenProductExistsThenSaveAndReturnUpdatedResponse() {
        // Given
        Product product = buildProduct();
        ProductUpdateRequest request = new ProductUpdateRequest("Updated description", "X-2025");
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        // When
        ProductResponse response = productService.updateProduct(PRODUCT_ID, request);

        // Then
        verify(productRepository).save(product);
        assertEquals("Updated description", response.descripcion());
        assertEquals("X-2025", response.modelo());
    }

    @Test
    @DisplayName("updateProduct - descripcion is null - description field unchanged")
    void updateProductWhenDescripcionIsNullThenDescriptionUnchanged() {
        // Given
        Product product = buildProduct();
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        // When
        productService.updateProduct(PRODUCT_ID, new ProductUpdateRequest(null, "X-2025"));

        // Then
        verify(productRepository).save(product);
        assertEquals("Gama alta 128GB", product.getDescription());
    }

    @Test
    @DisplayName("updateProduct - modelo is null - model field unchanged")
    void updateProductWhenModeloIsNullThenModelUnchanged() {
        // Given
        Product product = buildProduct();
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        // When
        productService.updateProduct(PRODUCT_ID, new ProductUpdateRequest("Updated description", null));

        // Then
        verify(productRepository).save(product);
        assertEquals("X-2024", product.getModel());
    }

    @Test
    @DisplayName("updateProduct - product not found - throws ProductNotFoundException")
    void updateProductWhenProductNotFoundThenThrowException() {
        // Given
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(PRODUCT_ID, new ProductUpdateRequest("Updated description", "X-2025")));
    }

    private Product buildProduct() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setName("Smartphone");
        product.setDescription("Gama alta 128GB");
        product.setPrice(new BigDecimal("599.99"));
        product.setModel("X-2024");
        return product;
    }
}
