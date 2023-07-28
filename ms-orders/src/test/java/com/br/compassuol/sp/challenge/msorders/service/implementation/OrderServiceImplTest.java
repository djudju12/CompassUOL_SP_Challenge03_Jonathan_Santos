package com.br.compassuol.sp.challenge.msorders.service.implementation;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.UpdateOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsResponse;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import com.br.compassuol.sp.challenge.msorders.model.entity.OrderedProduct;
import com.br.compassuol.sp.challenge.msorders.model.mapper.OrderMapper;
import com.br.compassuol.sp.challenge.msorders.repository.OrderRepository;
import com.br.compassuol.sp.challenge.msorders.service.AddressService;
import com.br.compassuol.sp.challenge.msorders.service.SenderMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {


    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SenderMessageService senderMessageService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private OrderServiceImpl orderService;
    private final Long USER_ID = 1L;
    private final Long ORDER_ID = 1L;



    @Test
    void findAllOrders() {
        // given

        // when

        // tshen
    }

    @Test
    void cancelOrder() {
    }

    @Test
    void updateOrder() {
    }

    @Nested
    public class MethodsThatExchangesDataWithOtherServices {

        private Order expectedOrder;
        private PayloadProductsRequest request;
        private PayloadProductsResponse response;

        @BeforeEach
        void setUp() {
            expectedOrder = new Order();
            expectedOrder.setProducts(List.of(new OrderedProduct()));

            // all tests will need this
            request = new PayloadProductsRequest();
            response = new PayloadProductsResponse();
            given(senderMessageService.getProductsDescription(request))
                    .willReturn(response);
        }

        @Test
        void createOrder() {
            // given - initial conditions
            DeliveryAddressDto deliveryAddress = new DeliveryAddressDto();
            ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
            response.setErrors(new HashMap<>());

            Order expectedOrder = new Order();
            OrderDto orderDto = new OrderDto()
                    .setUserId(USER_ID)
                    .setDeliveryAddress(deliveryAddress)
                    .setProducts(request);


            // given - this behavior
            given(senderMessageService.userExists(anyLong())).willReturn(true);
            given(addressService.completeThisAddress(deliveryAddress))
                    .willReturn(deliveryAddress);
            given(orderMapper.toEntity(orderDto)).willReturn(expectedOrder);
            given(orderRepository.save(expectedOrder)).willReturn(mock(Order.class));
            given(orderMapper.toDto(any(Order.class))).willReturn(any(OrderDto.class));

            // when
            OrderDto returnedOrder = orderService.createOrder(orderDto);

            //then - this should happen
            then(senderMessageService).should().userExists(USER_ID);
            then(senderMessageService).should().getProductsDescription(request);
            then(addressService).should().completeThisAddress(deliveryAddress);
            then(orderMapper).should().toEntity(orderDto);

            verify(orderRepository).save(captor.capture());
            Order capturedOrder = captor.getValue();
            assertThat(capturedOrder.getId()).isEqualTo(0);

            then(orderMapper).should().toDto(any(Order.class));
        }

        @Test
        void findWithDetails() {
            // given
            given(orderRepository.findByIdActive(anyLong())).willReturn(Optional.of(expectedOrder));
            given(orderMapper.toProductRequest(anyList())).willReturn(request);

            // when
            DetailedOrderDto returnedOrder = orderService.findWithDetails(ORDER_ID);

            // then
            then(orderRepository).should().findByIdActive(ORDER_ID);
            then(senderMessageService).should().getProductsDescription(request);
            then(orderMapper).should().toProductRequest(anyList());
            // TODO - nao tive tempo de veriricar
//            then(orderMapper).should().toDto(any(Order.class), anyList());
        }

    }
}