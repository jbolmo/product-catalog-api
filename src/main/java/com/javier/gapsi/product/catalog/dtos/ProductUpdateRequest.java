package com.javier.gapsi.product.catalog.dtos;

import jakarta.validation.constraints.Size;

public record ProductUpdateRequest(
        @Size(max = 200) String descripcion,
        @Size(max = 10) String modelo
) {}
