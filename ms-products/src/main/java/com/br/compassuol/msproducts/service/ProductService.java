package com.br.compassuol.msproducts.service;

import com.br.compassuol.msproducts.model.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAllProducts();
    ProductDto updateProduct(Long id, ProductDto product);
    void deleteProduct(Long id);
    ProductDto createProduct(ProductDto product);
}
