package com.br.compassuol.msproducts.controller;

import com.br.compassuol.msproducts.exception.ErrorResponse;
import com.br.compassuol.msproducts.model.dto.ProductDto;
import com.br.compassuol.msproducts.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Products", description = "Endpoints for products operations")
@ApiResponse(
        responseCode = "4xx"
        , description = "Unauthorized/Forbidden when token is invalid"
        , content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@SecurityRequirement(name = "Authorization")
@RestController
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
        summary = "Get Products"
        , description = "Retrieve all products"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Sort parameter is invalid"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<ProductDto>> getProducts(Pageable page) {
        // Default page properties in application.(yml|properties)
        List<ProductDto> productList = productService.findAllProducts(page);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @Operation(
        summary = "Retrieve a Product"
        , description = "Get an product by id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Product id not found"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Some field in request body is invalid"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping(value = "/{productId}", produces = "application/json")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId,
                                                    @Valid @RequestBody ProductDto product) {
        ProductDto updatedProduct = productService.updateProduct(productId, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @Operation(
        summary = "Delete a Product"
        , description = "Delete an product by id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "NO CONTENT"
                    , content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "404", description = "Product id not found"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        // This method in fact delete a product from the database
        productService.deleteProduct(productId);
    }

    @Operation(
        summary = "Create a Product"
        , description = "Create a new product"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "NO CONTENT"
                    ,content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "404", description = "Product id not found"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Some field is invalid"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping( value = "/")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto product) {
        ProductDto updatedProduct = productService.createProduct(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
    }

}
