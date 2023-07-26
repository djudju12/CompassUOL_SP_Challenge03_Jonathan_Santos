package com.br.compassuol.sp.challenge.msorders.service.implementation;

import com.br.compassuol.sp.challenge.msorders.model.dto.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.ProductListDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.OrderedProduct;
import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import com.br.compassuol.sp.challenge.msorders.model.mapper.OrderMapper;
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
    private final OrderMapper orderMapper;
    public OrderServiceImpl(OrderRepository orderRepository,
                            SenderMessageService senderMessageService,
                            OrderMapper orderMapper) {

        this.orderRepository = orderRepository;
        this.senderMessageService = senderMessageService;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        order.setId(0L);
        order.setStatus(OrderStatus.PENDING);
        Order newOrder = orderRepository.save(order);
        return orderMapper.toDto(newOrder);
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
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    // TODO - exception
    public void cancelOrder(long id) {
        Order order = orderRepository.findByIdActive(id).orElseThrow(
                () -> new RuntimeException("Order not found")
        );

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    @Override
    // TODO - exception
    public DetailedOrderDto findWithDetails(long oderId) {
        Order findOrder = orderRepository.findByIdActive(oderId).orElseThrow(
                () -> new RuntimeException("Order not found")
        );

        List<OrderedProduct> orderedProducts = findOrder.getProducts();
        ProductListDto items = orderMapper.orderedProductListToProductListDto(orderedProducts);
        List<ProductDto> details = senderMessageService.getProductsDescription(items);

        return orderMapper.toDto(findOrder, details);
    }

}
