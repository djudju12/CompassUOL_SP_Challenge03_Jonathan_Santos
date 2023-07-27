package com.br.compassuol.msproducts.model.mapper;

import com.br.compassuol.msproducts.model.dto.ProductDto;
import com.br.compassuol.msproducts.model.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductMapperImplTest {

    private ProductMapper productMapper;

    private final long ID = 1L;
    private final String NAME = "Product 1";
    private final String DESCRIPTION = "Description 1";
    private final BigDecimal PRICE = BigDecimal.ONE;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapperImpl();
    }

    @Test
    void toDto() {
        // Given
        Product product = new Product()
                .setId(ID)
                .setName(NAME)
                .setDescription(DESCRIPTION)
                .setPrice(PRICE);

        // When
        ProductDto productDto = productMapper.toDto(product);

        //then
        assertAll(
                () -> assertEquals(product.getId(), productDto.getId()),
                () -> assertEquals(product.getName(), productDto.getName()),
                () -> assertEquals(product.getDescription(), productDto.getDescription()),
                () -> assertEquals(product.getPrice(), productDto.getPrice())
        );
    }


    @Test
    void toEntity() {
        // Given
        ProductDto productDto = new ProductDto()
                .setId(ID)
                .setName(NAME)
                .setDescription(DESCRIPTION)
                .setPrice(PRICE);

        // When
        Product product = productMapper.toEntity(productDto);

        //then
        assertAll(
                () -> assertEquals(productDto.getId(), product.getId()),
                () -> assertEquals(productDto.getName(), product.getName()),
                () -> assertEquals(productDto.getDescription(), product.getDescription()),
                () -> assertEquals(productDto.getPrice(), product.getPrice())
        );
    }
}