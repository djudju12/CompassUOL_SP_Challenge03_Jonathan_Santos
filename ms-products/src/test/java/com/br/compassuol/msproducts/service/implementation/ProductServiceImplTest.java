package com.br.compassuol.msproducts.service.implementation;

import com.br.compassuol.msproducts.exception.types.ProductIdNotFoundException;
import com.br.compassuol.msproducts.model.dto.PayloadProducts;
import com.br.compassuol.msproducts.model.dto.ProductDto;
import com.br.compassuol.msproducts.model.entity.Product;
import com.br.compassuol.msproducts.model.mapper.ProductMapper;
import com.br.compassuol.msproducts.repository.ProductRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
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
    private final Long PRODUCT_NOT_FOUND_ID = 666L;

    private Product expectedProduct;

    @BeforeEach
    void setUp() {
        expectedProduct = new Product();
    }

    @Test
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
    void deleteProduct_ReceivesValidInput_() {
        // given
        given(productRepository.findById(PRODUCT_ID)).willReturn(Optional.of(expectedProduct));

        // when
        productService.deleteProduct(PRODUCT_ID);

        // then
        then(productRepository).should().findById(PRODUCT_ID);
        then(productRepository).should().delete(expectedProduct);
    }

    @Test
    void deleteProduct_ReceivesInvalidInput_Throws() {
        // given
        given(productRepository.findById(PRODUCT_NOT_FOUND_ID)).willReturn(Optional.empty());

        // when then
        assertThatExceptionOfType(ProductIdNotFoundException.class).isThrownBy(
                () -> productService.deleteProduct(PRODUCT_NOT_FOUND_ID));

        then(productRepository).should().findById(PRODUCT_NOT_FOUND_ID);
        then(productRepository).should(never()).delete(any(Product.class));
    }

    @Test
    void findAllById_ReceivesValidInput_ReturnPayload() {
        // given
        List<Long> input = Lists.newArrayList(PRODUCT_ID);

        given(productRepository.findById(PRODUCT_ID)).willReturn(Optional.of(expectedProduct));
        given(productMapper.toDto(expectedProduct)).willReturn(mock(ProductDto.class));

        // when
        PayloadProducts payload = productService.findAllById(input);

        // then
        then(productRepository).should(times(input.size())).findById(anyLong());
        then(productMapper).should(times(input.size())).toDto(any(Product.class));

        assertThat(payload.getErrors().size()).isEqualTo(0);
        assertThat(payload).isNotNull();

    }

    @Test
    void findAllById_ReceivesMixInput_ReturnPayload() {
        // given
        final int NUMBER_OF_VALID_INPUTS = 1;
        List<Long> input = Lists.newArrayList(PRODUCT_ID, PRODUCT_NOT_FOUND_ID);

        given(productMapper.toDto(expectedProduct)).willReturn(mock(ProductDto.class));
        given(productRepository.findById(PRODUCT_ID)).willReturn(Optional.of(expectedProduct));
        given(productRepository.findById(PRODUCT_NOT_FOUND_ID)).willReturn(Optional.empty());

        // when
        PayloadProducts payload = productService.findAllById(input);

        // then
        then(productRepository).should(times(input.size())).findById(anyLong());
        then(productMapper).should(times(NUMBER_OF_VALID_INPUTS)).toDto(any(Product.class));

        assertThat(payload.getErrors().size()).isEqualTo(NUMBER_OF_VALID_INPUTS);
        assertThat(payload).isNotNull();
    }

    @Test
    void updateProduct_ReceivesInvalidInput_Throws() {
        // given
        given(productRepository.existsById(PRODUCT_NOT_FOUND_ID)).willReturn(false);

        // when then
        assertThatExceptionOfType(ProductIdNotFoundException.class).isThrownBy(
                () -> productService.updateProduct(PRODUCT_NOT_FOUND_ID, any(ProductDto.class)));
        then(productRepository).should().existsById(PRODUCT_NOT_FOUND_ID);
        then(productRepository).should(never()).save(any(Product.class));
    }

    /*
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