package com.javier.gapsi.product.catalog.dtos;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        String modelo
) {}
