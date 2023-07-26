package com.br.compassuol.msproducts.service;

import com.br.compassuol.msproducts.model.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAllProducts(int page, int linesPerPage, String direction, String orderBy);
    ProductDto updateProduct(Long id, ProductDto product);
    void deleteProduct(Long id);
    ProductDto createProduct(ProductDto product);
    ProductDto findProductById(Long id);
}
