package com.javier.gapsi.product.catalog.services;

import com.javier.gapsi.product.catalog.dtos.ProductResponse;
import com.javier.gapsi.product.catalog.dtos.ProductUpdateRequest;
import com.javier.gapsi.product.catalog.entities.Product;
import com.javier.gapsi.product.catalog.exceptions.ProductNotFoundException;
import com.javier.gapsi.product.catalog.repositories.ProductRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private static final Logger LOGGER = LogManager.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse getProduct(String id) {
        return toResponse(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id)));
    }

    @Transactional
    public ProductResponse updateProduct(String id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (request.descripcion() != null) {
            product.setDescription(request.descripcion());
        }
        if (request.modelo() != null) {
            product.setModel(request.modelo());
        }

        LOGGER.atInfo()
                .log("Updated product with id: {}", id);

        return toResponse(productRepository.save(product));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getModel()
        );
    }
}
