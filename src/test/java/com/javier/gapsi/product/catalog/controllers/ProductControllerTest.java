package com.javier.gapsi.product.catalog.controllers;

import com.javier.gapsi.product.catalog.dtos.ProductResponse;
import com.javier.gapsi.product.catalog.dtos.ProductUpdateRequest;
import com.javier.gapsi.product.catalog.exceptions.ProductNotFoundException;
import com.javier.gapsi.product.catalog.services.ProductService;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@DisplayName("ProductController")
class ProductControllerTest {

    private static final String VALID_ID = "ART1234567";
    private static final ProductResponse PRODUCT_RESPONSE = new ProductResponse(
            VALID_ID, "Smartphone", "Gama alta 128GB", new BigDecimal("599.99"), "X-2024");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    @DisplayName("GET /{id} - product exists - returns 200 with full product body")
    void getArticuloWhenProductExistsThenReturn200() throws Exception {
        // Given
        when(productService.getProduct(VALID_ID)).thenReturn(PRODUCT_RESPONSE);

        // When / Then
        mockMvc.perform(get("/articulos/{id}", VALID_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VALID_ID))
                .andExpect(jsonPath("$.nombre").value("Smartphone"))
                .andExpect(jsonPath("$.descripcion").value("Gama alta 128GB"))
                .andExpect(jsonPath("$.precio").value(599.99))
                .andExpect(jsonPath("$.modelo").value("X-2024"));
    }

    @Test
    @DisplayName("GET /{id} - id shorter than 10 chars - returns 400")
    void getArticuloWhenIdTooShortThenReturn400() throws Exception {
        // Given / When / Then
        mockMvc.perform(get("/articulos/{id}", "SHORT"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /{id} - id longer than 10 chars - returns 400")
    void getArticuloWhenIdTooLongThenReturn400() throws Exception {
        // Given / When / Then
        mockMvc.perform(get("/articulos/{id}", "TOOLONGID12"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /{id} - product not found - returns 404")
    void getArticuloWhenProductNotFoundThenReturn404() throws Exception {
        // Given
        when(productService.getProduct(VALID_ID)).thenThrow(new ProductNotFoundException(VALID_ID));

        // When / Then
        mockMvc.perform(get("/articulos/{id}", VALID_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PATCH /{id} - valid body - returns 200 with updated product")
    void patchArticuloWhenValidRequestThenReturn200() throws Exception {
        // Given
        ProductResponse updatedResponse = new ProductResponse(
                VALID_ID, "Smartphone", "Updated description", new BigDecimal("599.99"), "X-2025");
        when(productService.updateProduct(VALID_ID, new ProductUpdateRequest("Updated description", "X-2025")))
                .thenReturn(updatedResponse);

        // When / Then
        mockMvc.perform(patch("/articulos/{id}", VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"descripcion": "Updated description", "modelo": "X-2025"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Updated description"))
                .andExpect(jsonPath("$.modelo").value("X-2025"));
    }

    @Test
    @DisplayName("PATCH /{id} - unknown field in body - returns 400")
    void patchArticuloWhenUnknownFieldInBodyThenReturn400() throws Exception {
        // Given / When / Then
        mockMvc.perform(patch("/articulos/{id}", VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"precio": 599.99}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PATCH /{id} - descripcion exceeds 200 chars - returns 400")
    void patchArticuloWhenDescripcionTooLongThenReturn400() throws Exception {
        // Given
        String longDescription = "a".repeat(201);

        // When / Then
        mockMvc.perform(patch("/articulos/{id}", VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"descripcion\": \"%s\"}", longDescription)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PATCH /{id} - product not found - returns 404")
    void patchArticuloWhenProductNotFoundThenReturn404() throws Exception {
        // Given
        when(productService.updateProduct(VALID_ID, new ProductUpdateRequest("Updated description", null)))
                .thenThrow(new ProductNotFoundException(VALID_ID));

        // When / Then
        mockMvc.perform(patch("/articulos/{id}", VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"descripcion": "Updated description"}
                                """))
                .andExpect(status().isNotFound());
    }
}
