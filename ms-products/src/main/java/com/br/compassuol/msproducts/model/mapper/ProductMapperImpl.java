package com.br.compassuol.msproducts.model.mapper;

import com.br.compassuol.msproducts.model.dto.ProductDto;
import com.br.compassuol.msproducts.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductDto toDto(Product product) {
        return new ProductDto()
                .setId(product.getId())
                .setDescription(product.getDescription())
                .setPrice(product.getPrice())
                .setName(product.getName());
    }

    @Override
    public Product toEntity(ProductDto productDto) {
        return new Product()
                .setId(productDto.getId())
                .setDescription(productDto.getDescription())
                .setPrice(productDto.getPrice())
                .setName(productDto.getName());
    }
}
