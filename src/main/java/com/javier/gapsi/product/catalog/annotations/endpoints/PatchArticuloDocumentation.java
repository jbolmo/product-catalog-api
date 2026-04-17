package com.javier.gapsi.product.catalog.annotations.endpoints;

import com.javier.gapsi.product.catalog.dtos.ProductResponse;
import com.javier.gapsi.product.catalog.dtos.ProductUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Partial update of article description and model",
        description = "Updates mutable fields (descripcion, modelo). Immutable fields (id, nombre, precio) cannot be modified; sending them returns HTTP 400."
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Update successful",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProductResponse.class),
                        examples = @ExampleObject(value = """
                                {
                                  "id": "ART1234567",
                                  "nombre": "Smartphone",
                                  "descripcion": "Updated description",
                                  "precio": 599.99,
                                  "modelo": "X-2025"
                                }
                                """)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Validation error or attempt to modify immutable fields",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(value = """
                                {
                                  "type": "about:blank",
                                  "title": "Bad Request",
                                  "status": 400,
                                  "detail": "Field 'precio' is not allowed"
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
@RequestBody(
        required = true,
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductUpdateRequest.class),
                examples = @ExampleObject(value = """
                        {
                          "descripcion": "Updated description",
                          "modelo": "X-2025"
                        }
                        """)
        )
)
public @interface PatchArticuloDocumentation {
}
