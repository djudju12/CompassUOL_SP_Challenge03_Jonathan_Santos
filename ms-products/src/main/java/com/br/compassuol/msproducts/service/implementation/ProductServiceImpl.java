package com.br.compassuol.msproducts.service.implementation;

import com.br.compassuol.msproducts.exception.types.ProductIdNotFoundException;
import com.br.compassuol.msproducts.model.dto.PayloadProducts;
import com.br.compassuol.msproducts.model.dto.ProductDto;
import com.br.compassuol.msproducts.model.entity.Product;
import com.br.compassuol.msproducts.model.mapper.ProductMapper;
import com.br.compassuol.msproducts.repository.ProductRepository;
import com.br.compassuol.msproducts.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDto> findAllProducts(Pageable page) {
        return productRepository.findAll(page)
                .getContent()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto product) {
        if (!productRepository.existsById(id))
            throw new ProductIdNotFoundException(id);

        product.setId(id);
        Product productToUpdate = productMapper.toEntity(product);
        Product updatedProduct = productRepository.save(productToUpdate);
        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductIdNotFoundException(id));

        productRepository.delete(product);
    }

    @Override
    public ProductDto createProduct(ProductDto product) {
        Product productToCreate = productMapper.toEntity(product);
        productToCreate.setId(0L);
        Product createdProduct = productRepository.save(productToCreate);
        return productMapper.toDto(createdProduct);
    }

    @Override
    public PayloadProducts findAllById(List<Long> ids) {
        PayloadProducts payload = new PayloadProducts();
        List<ProductDto> products = new ArrayList<>();
        Map<Long, String> errors = new HashMap<>();

        for (Long id : ids) {
            Optional<Product> p = productRepository.findById(id);
            if (p.isPresent()) {
                products.add(productMapper.toDto(p.get()));
            } else {
                errors.put(id, "Product not found");
            }
        }

        payload.setProducts(products);
        payload.setErrors(errors);
        return payload;
    }

}
