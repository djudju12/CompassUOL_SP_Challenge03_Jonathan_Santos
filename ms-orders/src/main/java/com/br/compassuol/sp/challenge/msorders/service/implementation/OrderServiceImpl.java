package com.br.compassuol.sp.challenge.msorders.service.implementation;

import com.br.compassuol.sp.challenge.msorders.model.dto.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.ProductListDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.Cart;
import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import com.br.compassuol.sp.challenge.msorders.repository.OrderRepository;
import com.br.compassuol.sp.challenge.msorders.service.OrderService;
import com.br.compassuol.sp.challenge.msorders.service.SenderMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final SenderMessageService senderMessageService;
    public OrderServiceImpl(OrderRepository orderRepository,
                            SenderMessageService senderMessageService) {
        this.orderRepository = orderRepository;
        this.senderMessageService = senderMessageService;
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
    public void findWithDetails(long oderId) {
        List<Long> idList = orderRepository.findById(oderId).orElseThrow(
                        () -> new RuntimeException("Order not found"))
                .getProducts()
                .stream()
                .map(Cart::getProductId)
                .toList();

        ProductListDto items = new ProductListDto(idList);
        List<ProductDto> result = senderMessageService.getProductsDescription(items);
        log.info("result: {}", result);
    }


    // TODO - criar mapper
    private OrderDto toDto(Order order) {
        List<Long> productsIds = order.getProducts()
                .stream()
                .map(Cart::getProductId)
                .toList();

        return new OrderDto()
                .setId(order.getId())
                .setUserId(order.getUserId())
                .setStatus(order.getStatus())
                .setProducts(new ProductListDto(productsIds));
    }

    private Order toEntity(OrderDto orderDto) {
        List<Cart> products = orderDto.getProducts()
                .getIds()
                .stream()
                .map(Cart::new)
                .toList();

        return new Order()
                .setId(orderDto.getId())
                .setStatus(orderDto.getStatus())
                .setProducts(products)
                .setUserId(orderDto.getUserId());
    }

}
