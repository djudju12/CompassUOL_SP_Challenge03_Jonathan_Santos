package com.br.compassuol.sp.challenge.msorders.controller;

import com.br.compassuol.sp.challenge.msorders.exception.ErrorResponse;
import com.br.compassuol.sp.challenge.msorders.exception.types.OrderIdNotFoundException;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.UpdateOrderDto;
import com.br.compassuol.sp.challenge.msorders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Orders", description = "Endpoints for orders operations")
@ApiResponse(
    responseCode = "4xx"
    , description = "Unauthorized/Forbidden when token is invalid"
    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
@SecurityRequirement(name = "Authorization")
@RestController
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
        summary = "Get Orders"
        , description = "Retrive all active orders"
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = {"/", ""}, produces = "application/json")
    public ResponseEntity<List<OrderDto>> getProducts(Pageable page) {
        // Page settings are defined in application.file
        List<OrderDto> orderList = orderService.findAllOrders(page);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @Operation(
        summary = "Retrieve an Detailed Order"
        , description = "Get an order with details about products"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Order id not found"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(value = "/{orderId}", produces = "application/json")
    public ResponseEntity<DetailedOrderDto> getProducts(@PathVariable long orderId) {
        DetailedOrderDto orderDto = orderService.findWithDetails(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @Operation(
        summary = "Create an Order"
        , description = "Create a new order with status PENDING"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "404", description = "User | Product id not found"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping(value = {"/", ""}, produces = "application/json")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderDto newOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Cancel an Order"
        , description = "Cancel the order, changing the status to CANCELED"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "NO CONTENT"),
        @ApiResponse(responseCode = "404", description = "Order id not found"
                     , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable long orderId) {
        // I used DELETE verb because I think it's a more semantic way to cancel an order.
        orderService.cancelOrder(orderId);
    }

    @Operation(
        summary = "Update an Order"
        , description = "Update the the status of the order. " +
                        "To cancel an order you must use DELETE HTTP verb."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Trying to cancel an order with PUT HTTP verb"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Order id not found"
                , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping(value = "/{orderId}", produces = "application/json")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable long orderId,
                                                @Valid @RequestBody UpdateOrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(orderId, orderDto);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}
