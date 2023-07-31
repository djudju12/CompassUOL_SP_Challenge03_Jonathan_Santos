package com.br.compassuol.sp.challenge.msorders.controller;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.UpdateOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;
import com.br.compassuol.sp.challenge.msorders.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrdersController.class)
class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private final String ORDER_URL = "/";
    private final long ORDER_ID = 1L;

    @AfterEach
    void tearDown() {
        reset(orderService);
    }

    @Test
    void getProducts() throws Exception {
        // given
        List<OrderDto> orderList = Lists.newArrayList(new OrderDto());

        given(orderService.findAllOrders(any(Pageable.class))).willReturn(orderList);

        // when then
        mockMvc.perform(get(ORDER_URL)
                    .accept(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(orderList.size()));

        then(orderService).should().findAllOrders(any(Pageable.class));
    }

    @Test
    void testGetProducts() throws Exception {
        // given
        DetailedOrderDto expectedOrder = new DetailedOrderDto().setId(ORDER_ID);
        given(orderService.findWithDetails(anyLong())).willReturn(expectedOrder);

        // when then
        mockMvc.perform(get(ORDER_URL + ORDER_ID)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        then(orderService).should().findWithDetails(ORDER_ID);
    }

    @Test
    void createOrder() throws Exception {
        // given
        PayloadProductsRequest products = new PayloadProductsRequest();
        products.setIds(Lists.newArrayList(1L, 2L));
        OrderDto orderDto = new OrderDto()
                .setId(ORDER_ID)
                .setUserId(1L)
                .setDeliveryAddress(new DeliveryAddressDto())
                .setProducts(products);

        ObjectMapper objectMapper = new ObjectMapper();

        given(orderService.createOrder(any(OrderDto.class))).willReturn(any(OrderDto.class));

        // when then
        mockMvc.perform(post(ORDER_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto))
                )
                .andExpect(status().isCreated());

        then(orderService).should().createOrder(any(OrderDto.class));
    }

    @Test
    void cancelOrder() throws Exception {
        // given
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        //when then
        mockMvc.perform(delete(ORDER_URL + ORDER_ID)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        verify(orderService).cancelOrder(captor.capture());
        Long capturedId = captor.getValue();
        assertThat(capturedId).isEqualTo(ORDER_ID);
    }

    @Test
    void updateOrder() throws Exception {
        // given
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        given(orderService.updateOrder(anyLong(), any(UpdateOrderDto.class))).willReturn(mock(OrderDto.class));

        //when then
        mockMvc.perform(put(ORDER_URL + ORDER_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(status().isOk());

        verify(orderService).updateOrder(captor.capture(), any(UpdateOrderDto.class));
        Long capturedId = captor.getValue();
        assertThat(capturedId).isEqualTo(ORDER_ID);

    }
}