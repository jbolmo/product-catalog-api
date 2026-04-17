package com.javier.gapsi.product.catalog.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id", length = 10, nullable = false, updatable = false)
    private String id;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "model", length = 10)
    private String model;

    @Column(name = "name", length = 20, nullable = false, updatable = false)
    private String name;

    @Column(name = "price", precision = 10, scale = 2, nullable = false, updatable = false)
    private BigDecimal price;

    public Product() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
