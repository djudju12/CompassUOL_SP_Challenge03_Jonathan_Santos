package com.br.compassuol.msproducts.controller;

import com.br.compassuol.msproducts.model.dto.ProductDto;
import com.br.compassuol.msproducts.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // TODO - default na properties
    // TODO - validar os parametros
    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<ProductDto>> getProducts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int linesPerPage,
                                                        @RequestParam(defaultValue = "asc") String direction,
                                                        @RequestParam(defaultValue = "name") String orderBy) {

        List<ProductDto> productList = productService.findAllProducts(page, linesPerPage, direction, orderBy);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
                                                    @Valid @RequestBody ProductDto product) {
        ProductDto updatedProduct = productService.updateProduct(id, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping(value = {"/", ""})
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto product) {
        ProductDto updatedProduct = productService.createProduct(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
    }

}
