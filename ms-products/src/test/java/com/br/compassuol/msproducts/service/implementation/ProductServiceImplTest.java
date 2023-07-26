package com.br.compassuol.msproducts.service.implementation;

import com.br.compassuol.msproducts.model.dto.ProductDto;
import com.br.compassuol.msproducts.model.entity.Product;
import com.br.compassuol.msproducts.model.mapper.ProductMapper;
import com.br.compassuol.msproducts.repository.ProductRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private final Long PRODUCT_ID = 1L;

    private Product expectedProduct;

    @BeforeEach
    void setUp() {
        expectedProduct = new Product();
    }

    @Test
    @Disabled
    void findAllProducts() {
        // given
        List<Product> productList = Lists.newArrayList(mock(Product.class));
        Page<Product> productPage = new PageImpl<>(productList);
        given(productRepository.findAll(any(PageRequest.class))).willReturn(productPage);

        // when
        List<ProductDto> returnedProductsDto = productService.findAllProducts(mock(PageRequest.class));

        // then
        then(productRepository).should().findAll(any(PageRequest.class));
        then(productMapper).should(times(productList.size())).toDto(any(Product.class));
        assertThat(returnedProductsDto.size()).isEqualTo(productList.size());
    }

    @Test
    void deleteProduct_ReceivesIdThatExists_() {
        // given
        given(productRepository.findById(PRODUCT_ID)).willReturn(Optional.of(expectedProduct));

        // when
        productService.deleteProduct(PRODUCT_ID);

        // then
        then(productRepository).should().findById(PRODUCT_ID);
        then(productRepository).should().delete(expectedProduct);
    }

    /**
     * Nested classes are used to group tests that are related to the same action (modification of entities).
     * This way, we can have a better organization of our tests and reuse some mocks.
     */
    @Nested
    public class CreationTests {

        private ProductDto productDto;

        @BeforeEach
        void setUp() {
            productDto = new ProductDto();

            given(productMapper.toEntity(productDto)).willReturn(expectedProduct);
            given(productRepository.save(expectedProduct)).willReturn(mock(Product.class));
            given(productMapper.toDto(any(Product.class))).willReturn(mock(ProductDto.class));
        }

        @Test
        void createProduct() {
            // given
            ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

            // when
            ProductDto returnedProductDto = productService.createProduct(productDto);

            //then
            then(productMapper).should().toEntity(productDto);

            verify(productRepository).save(productArgumentCaptor.capture());
            Product capturedProduct = productArgumentCaptor.getValue();
            assertThat(capturedProduct.getId()).isEqualTo(0);

            then(productMapper).should().toDto(any(Product.class));

            assertThat(returnedProductDto).isNotNull();
        }

        @Test
        void updateProduct_ReceivesValidInput_ReturnDto() {
            // given
            // toEntity, save, toDto are mocked in the @BeforeEach
            ArgumentCaptor<ProductDto> productDtoArgumentCaptor = ArgumentCaptor.forClass(ProductDto.class);
            given(productRepository.existsById(PRODUCT_ID)).willReturn(true);

            // when
            ProductDto returnedProductDto = productService.updateProduct(PRODUCT_ID, productDto);

            // then
            then(productRepository).should().existsById(PRODUCT_ID);

            verify(productMapper).toEntity(productDtoArgumentCaptor.capture());
            ProductDto capturedProductDto = productDtoArgumentCaptor.getValue();
            assertThat(capturedProductDto.getId()).isEqualTo(PRODUCT_ID);

            then(productRepository).should().save(expectedProduct);
            then(productMapper).should().toDto(any(Product.class));

            assertThat(returnedProductDto).isNotNull();
        }

    }


}