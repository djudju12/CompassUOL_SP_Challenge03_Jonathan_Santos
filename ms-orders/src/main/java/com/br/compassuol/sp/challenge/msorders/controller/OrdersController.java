package com.br.compassuol.sp.challenge.msorders.controller;

import com.br.compassuol.sp.challenge.msorders.model.dto.orders.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.UpdateOrderDto;
import com.br.compassuol.sp.challenge.msorders.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<OrderDto>> getProducts(Pageable page) {
        // Page settings are defined in application.file
        List<OrderDto> orderList = orderService.findAllOrders(page);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<DetailedOrderDto> getProducts(@PathVariable long orderId) {
        DetailedOrderDto orderDto = orderService.findWithDetails(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PostMapping(value = {"/", ""})
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderDto newOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }


    /*
     * I use DELETE verb because I think it's a more semantic way to cancel an order.
     * But remember that this method just set the order status to CANCELED (27/06/2023).
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable long id) {
        orderService.cancelOrder(id);
    }

    /*
     * This method update just the order status, but note that is not possible to change to CANCELED.
     * To cancel an order, use DELETE verb.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable long id,
                                                @Valid @RequestBody UpdateOrderDto orderDto) {

        OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}
