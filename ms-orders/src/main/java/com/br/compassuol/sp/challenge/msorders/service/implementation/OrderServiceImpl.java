package com.br.compassuol.sp.challenge.msorders.service.implementation;

import com.br.compassuol.sp.challenge.msorders.model.dto.CartDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.OrderItemsDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.Cart;
import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import com.br.compassuol.sp.challenge.msorders.rabbitmq.RabbitProducer;
import com.br.compassuol.sp.challenge.msorders.repository.OrderRepository;
import com.br.compassuol.sp.challenge.msorders.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RabbitProducer rabbitProducer;
    public OrderServiceImpl(OrderRepository orderRepository, RabbitProducer rabbitProducer) {
        this.orderRepository = orderRepository;
        this.rabbitProducer = rabbitProducer;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = toEntity(orderDto);
        order.setId(0L);
        order.setStatus(OrderStatus.PENDING);
        Order newOrder = orderRepository.save(order);
        return toDto(newOrder);
    }

    @Override
    public List<OrderDto> findAllOrders(int page, int linesPerPage, String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Sort.Direction.valueOf(direction.toUpperCase()),
                orderBy
        );

        return orderRepository.findAllActiveOrders(pageRequest)
                .getContent()
                .stream()
                .map(this::toDto)
                .toList();
    }

        @Override
    // TODO - exception
    public void cancelOrder(long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found")
        );

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    @Override
    public void findWithDetails(long id) {
        List<CartDto> items = orderRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Order not found"))
                .getProducts().stream()
                .map(cart -> new CartDto(cart.getProductId()))
                .toList();
        OrderItemsDto dto = new OrderItemsDto(items.stream().map(CartDto::getProductId).toList());
        rabbitProducer.send(dto);
    }


    // TODO - criar mapper
    private OrderDto toDto(Order order) {
        return new OrderDto()
                .setId(order.getId())
                .setUserId(order.getUserId())
                .setStatus(order.getStatus())
                .setProducts(
                        order.getProducts()
                                .stream()
                                .map(cart -> new CartDto(cart.getProductId()))
                                .collect(Collectors.toList()));
    }

    private Order toEntity(OrderDto orderDto) {
        return new Order()
                .setId(orderDto.getId())
                .setStatus(orderDto.getStatus())
                .setProducts(orderDto.getProducts()
                        .stream()
                        .map(dto -> new Cart().setProductId(dto.getProductId()))
                        .toList())

                .setUserId(orderDto.getUserId());
    }

}
