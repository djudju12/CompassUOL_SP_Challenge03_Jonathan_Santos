//package com.br.compassuol.msproducts.controller;
//
//import com.br.compassuol.msproducts.exception.ExceptionHandlers;
//import com.br.compassuol.msproducts.exception.types.ProductIdNotFoundException;
//import com.br.compassuol.msproducts.model.dto.ProductDto;
//import com.br.compassuol.msproducts.model.mapper.ProductMapper;
//import com.br.compassuol.msproducts.repository.ProductRepository;
//import com.br.compassuol.msproducts.service.implementation.ProductServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.json.JacksonTester;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.mock;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(ExceptionHandlers.class)
//public class ControllerExceptionHandlerTest {
//
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private ProductMapper productMapper;
//
//    @InjectMocks
//    private ProductServiceImpl productServiceI;
//
//    private final long NOT_FOUND_ID = 999L;
//    private final String PRODUCTS_URL = "/products/";
//
//    private MockMvc mockMvc;
//
//    public ControllerExceptionHandlerTest() {
//    }
//
//    @BeforeEach
//    void setUp() {
//        JacksonTester.initFields(this, new ObjectMapper());
//        this.mockMvc = MockMvcBuilders
//                .standaloneSetup(productController)
//                .setControllerAdvice(new ExceptionHandlers())
//                .build();
//    }
//
//    @Test
//    void updateProduct_ReceivesNotValidId_Throws() throws Exception {
//        // given
//        ProductDto productDto = mock(ProductDto.class);
//        given(productService.updateProduct(anyLong(), any(ProductDto.class)))
//                .willThrow(new ProductIdNotFoundException(NOT_FOUND_ID));
//
//        // when then
//        mockMvc.perform(put("/products/" + NOT_FOUND_ID)
//                        .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isNotFound())
//                .andDo(print());
//
////        then(productService).should().updateProduct(NOT_FOUND_ID, any());
//    }
//}
