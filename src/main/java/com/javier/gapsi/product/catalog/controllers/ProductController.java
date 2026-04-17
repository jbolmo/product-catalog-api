package com.javier.gapsi.product.catalog.controllers;

import com.javier.gapsi.product.catalog.annotations.endpoints.GetArticuloDocumentation;
import com.javier.gapsi.product.catalog.annotations.endpoints.PatchArticuloDocumentation;
import com.javier.gapsi.product.catalog.dtos.ProductResponse;
import com.javier.gapsi.product.catalog.dtos.ProductUpdateRequest;
import com.javier.gapsi.product.catalog.services.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/articulos")
@Tag(name = "Product Catalog", description = "API for querying and partially updating catalog articles")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetArticuloDocumentation
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getArticulo(
            @PathVariable @Size(min = 10, max = 10, message = "ID must be exactly 10 characters") String id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PatchArticuloDocumentation
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> patchArticulo(
            @PathVariable String id,
            @Valid @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }
}
