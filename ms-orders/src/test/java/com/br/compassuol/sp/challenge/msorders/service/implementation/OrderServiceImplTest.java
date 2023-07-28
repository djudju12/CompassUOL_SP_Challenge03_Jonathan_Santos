package com.br.compassuol.sp.challenge.msorders.service.implementation;

import com.br.compassuol.sp.challenge.msorders.exception.types.OrderIdNotFoundException;
import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.UpdateOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsResponse;
import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import com.br.compassuol.sp.challenge.msorders.model.entity.OrderedProduct;
import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import com.br.compassuol.sp.challenge.msorders.model.mapper.OrderMapper;
import com.br.compassuol.sp.challenge.msorders.repository.OrderRepository;
import com.br.compassuol.sp.challenge.msorders.service.AddressService;
import com.br.compassuol.sp.challenge.msorders.service.MessageSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {


    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MessageSenderService messageSenderService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private OrderServiceImpl orderService;
    private final Long USER_ID = 1L;
    private final Long ORDER_ID = 1L;
    Order expectedOrder;

    @BeforeEach
    void setUp() {
        expectedOrder = new Order();
        expectedOrder.setProducts(List.of(new OrderedProduct()));
    }

        @Test
    void findAllOrders_ReceivesValidPage_ReturnListOrderDto() {
        // given
        List<Order> orders = List.of(expectedOrder);
        PageImpl<Order> expectedPage = new PageImpl<>(orders);
        Pageable page = mock(Pageable.class);
        given(orderRepository.findAllByStatusIsNot(OrderStatus.CANCELED, page))
                .willReturn(expectedPage);
        given(orderMapper.toDto(any(Order.class))).willReturn(new OrderDto());

        // when
        List<OrderDto> ordersDto = orderService.findAllOrders(page);

        // then
        assertThat(ordersDto).isNotNull();
        then(orderRepository).should().findAllByStatusIsNot(OrderStatus.CANCELED, page);
        then(orderMapper).should(times((int) expectedPage.getTotalElements())).toDto(any(Order.class));
    }

    @Test
    void cancelOrder_ReceivesValidInput_() {
        // given
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        given(orderRepository.findByIdActive(ORDER_ID)).willReturn(Optional.of(expectedOrder));

        // when
        orderService.cancelOrder(ORDER_ID);

        // then
        verify(orderRepository).save(captor.capture());
        Order capturedOrder = captor.getValue();
        assertThat(capturedOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    void cancelOrder_ReceivesInValidInput_Throws() {
        // given
        given(orderRepository.findByIdActive(ORDER_ID)).willReturn(Optional.empty());

        // when then
        assertThatExceptionOfType(OrderIdNotFoundException.class).isThrownBy(
                () -> orderService.cancelOrder(ORDER_ID));

        then(orderRepository).should().findByIdActive(ORDER_ID);
        then(orderRepository).should(never()).save(any(Order.class));
    }

    @Test
    void updateOrder() {
        // given
        Order order = new Order().setStatus(OrderStatus.PENDING);
        UpdateOrderDto newOrder = new UpdateOrderDto();
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        newOrder.setStatus(OrderStatus.SHIPPED.name());

        given(orderRepository.findByIdActive(ORDER_ID)).willReturn(Optional.of(order));
        given(orderRepository.save(any(Order.class))).willReturn(expectedOrder);

        // when
        OrderDto returnedOrder = orderService.updateOrder(ORDER_ID, newOrder);

        // then
        verify(orderRepository).save(captor.capture());
        Order capturedOrder = captor.getValue();
        assertThat(capturedOrder.getStatus()).isEqualTo(OrderStatus.SHIPPED);

        then(orderRepository).should().findByIdActive(ORDER_ID);
        then(orderRepository).should().save(any(Order.class));
        then(orderMapper).should().toDto(expectedOrder);
    }

    @Nested
    public class MethodsThatExchangesDataWithOtherServices {

        private PayloadProductsRequest request;
        private PayloadProductsResponse response;

        @BeforeEach
        void setUp() {
            // all tests will need this
            request = new PayloadProductsRequest();
            response = new PayloadProductsResponse();
            given(messageSenderService.getProductsDescription(request))
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
            given(messageSenderService.userExists(anyLong())).willReturn(true);
            given(addressService.completeThisAddress(deliveryAddress))
                    .willReturn(deliveryAddress);
            given(orderMapper.toEntity(orderDto)).willReturn(expectedOrder);
            given(orderRepository.save(expectedOrder)).willReturn(mock(Order.class));
            given(orderMapper.toDto(any(Order.class))).willReturn(any(OrderDto.class));

            // when
            OrderDto returnedOrder = orderService.createOrder(orderDto);

            //then - this should happen
            then(messageSenderService).should().userExists(USER_ID);
            then(messageSenderService).should().getProductsDescription(request);
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
            then(messageSenderService).should().getProductsDescription(request);
            then(orderMapper).should().toProductRequest(anyList());
        }

    }
}