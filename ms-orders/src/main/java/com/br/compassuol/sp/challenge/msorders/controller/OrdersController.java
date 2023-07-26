package com.br.compassuol.sp.challenge.msorders.controller;

import com.br.compassuol.sp.challenge.msorders.model.dto.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.OrderDto;
import com.br.compassuol.sp.challenge.msorders.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    // TODO - default na properties
    // TODO - validar os parametros
    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<OrderDto>> getProducts(  @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int linesPerPage,
                                                        @RequestParam(defaultValue = "asc") String direction,
                                                        @RequestParam(defaultValue = "name") String orderBy) {

        List<OrderDto> orderList = orderService.findAllOrders(page, linesPerPage, direction, orderBy);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<DetailedOrderDto> getProducts(@PathVariable long orderId) {
        DetailedOrderDto orderDto = orderService.findWithDetails(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PostMapping(value = {"/", ""})
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderDto order = orderService.createOrder(orderDto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable long id) {
        orderService.cancelOrder(id);
    }

}
