package com.javier.gapsi.product.catalog.repositories;

import com.javier.gapsi.product.catalog.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
