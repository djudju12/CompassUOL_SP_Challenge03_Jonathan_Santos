package com.br.compassuol.msproducts.service;

import com.br.compassuol.msproducts.model.dto.PayloadProducts;
import com.br.compassuol.msproducts.model.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAllProducts(Pageable page);
    ProductDto updateProduct(Long id, ProductDto product);
    void deleteProduct(Long id);
    ProductDto createProduct(ProductDto product);
    PayloadProducts findAllById(List<Long> ids);
}
