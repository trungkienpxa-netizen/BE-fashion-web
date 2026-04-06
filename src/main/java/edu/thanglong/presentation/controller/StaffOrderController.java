package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.UpdateOrderStatusRequest;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.OrderResponse;
import edu.thanglong.usecase.order.OrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/staff/orders")
@PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
@RequiredArgsConstructor
public class StaffOrderController {

    private final OrderUseCase orderUseCase;

    @GetMapping
    public ApiResponse<List<OrderResponse>> getAllOrders() {
        return ApiResponse.success(
            orderUseCase.getAllOrders().stream().map(OrderResponse::from).toList()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getById(@PathVariable String id) {
        return ApiResponse.success(OrderResponse.from(orderUseCase.getById(id)));
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<OrderResponse> updateStatus(@PathVariable String id,
                                                   @Valid @RequestBody UpdateOrderStatusRequest req) {
        return ApiResponse.success(
            OrderResponse.from(orderUseCase.updateStatus(id, req.getStatus()))
        );
    }
}