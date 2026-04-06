package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.PlaceOrderRequest;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.OrderResponse;
import edu.thanglong.usecase.order.OrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderUseCase orderUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<OrderResponse> placeOrder(Authentication authentication,
                                                 @Valid @RequestBody PlaceOrderRequest request) {
        return ApiResponse.success(
            OrderResponse.from(orderUseCase.placeOrder(
                (String) authentication.getPrincipal(), request))
        );
    }

    @GetMapping("/my")
    public ApiResponse<List<OrderResponse>> getMyOrders(Authentication authentication) {
        return ApiResponse.success(
            orderUseCase.getMyOrders((String) authentication.getPrincipal())
                .stream().map(OrderResponse::from).toList()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getById(@PathVariable String id) {
        return ApiResponse.success(OrderResponse.from(orderUseCase.getById(id)));
    }

    @PatchMapping("/{id}/cancel")
    public ApiResponse<OrderResponse> cancel(Authentication authentication,
                                             @PathVariable String id) {
        return ApiResponse.success(
            OrderResponse.from(orderUseCase.cancelOrder(
                (String) authentication.getPrincipal(), id))
        );
    }
}