package com.javier.gapsi.product.catalog.annotations.endpoints;

import com.javier.gapsi.product.catalog.dtos.ProductResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Get article detail",
        description = "Retrieves a product from the catalog by its 10-character alphanumeric ID"
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Product found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProductResponse.class),
                        examples = @ExampleObject(value = """
                                {
                                  "id": "ART1234567",
                                  "nombre": "Smartphone",
                                  "descripcion": "Gama alta 128GB",
                                  "precio": 599.99,
                                  "modelo": "X-2024"
                                }
                                """)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Article not found",
                content = @Content(mediaType = "application/json")
        )
})
@CommonApiResponses
@Parameter(
        name = "id",
        description = "Article ID (exactly 10 alphanumeric characters)",
        required = true,
        schema = @Schema(type = "string", minLength = 10, maxLength = 10, pattern = "[A-Z0-9]{10}")
)
public @interface GetArticuloDocumentation {
}
