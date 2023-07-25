package com.br.compassuol.msproducts.controller;

import com.br.compassuol.msproducts.model.dto.ProductDto;
import com.br.compassuol.msproducts.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final String PRODUCTS_URL = "/products/";
    private final long PRODUCTS_ID = 1;
    private final String PRODUCTS_NAME = "foo";
    private final BigDecimal PRODUCTS_PRICE = BigDecimal.valueOf(10.0);
    private final String PRODUCTS_DESCRIPTION = "foo bar";
    private ProductDto productDto;

    @AfterEach
    void tearDown() {
        reset(productService);
    }

    @BeforeEach
    void setUp() {
        productDto = new ProductDto()
                .setId(PRODUCTS_ID)
                .setName(PRODUCTS_NAME)
                .setPrice(PRODUCTS_PRICE)
                .setDescription(PRODUCTS_DESCRIPTION);
    }

    // TODO - testar validação do DTO, Excessões personalizadas
    @Test
    void getProducts__ReturnsDtoList() throws Exception {
        // given
        List<ProductDto> productList = new ArrayList<>();
        given(productService.findAllProducts()).willReturn(productList);

        //when then
        mockMvc.perform(get(PRODUCTS_URL)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(productService).should().findAllProducts();

    }

    @Test
    void deleteProduct_ReceivesValidId_ReturnsNoContent() throws Exception {
        //when then
        mockMvc.perform(delete(PRODUCTS_URL + PRODUCTS_ID)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        then(productService).should().deleteProduct(PRODUCTS_ID);
    }

    @Test
    void updateProduct_ReceivesValidDto_ReturnOk() throws Exception {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        given(productService.updateProduct(PRODUCTS_ID, productDto)).willReturn(productDto);

        // when then
        mockMvc.perform(put(PRODUCTS_URL + PRODUCTS_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto))
                )
                .andExpect(status().isOk());

        then(productService).should().updateProduct(PRODUCTS_ID, productDto);
    }

    @Test
    void createProduct_ReceivesValidDto_ReturnOk() throws Exception {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        given(productService.createProduct(productDto)).willReturn(productDto);

        // when then
        mockMvc.perform(post(PRODUCTS_URL)
                    .content(objectMapper.writeValueAsString(productDto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isCreated());

        then(productService).should().createProduct(productDto);
    }
}