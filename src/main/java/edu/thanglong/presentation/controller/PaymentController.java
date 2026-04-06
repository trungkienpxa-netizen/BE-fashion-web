package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.PaymentCallbackRequest;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.PaymentResponse;
import edu.thanglong.usecase.payment.PaymentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @GetMapping("/order/{orderId}")
    public ApiResponse<PaymentResponse> getByOrderId(@PathVariable String orderId) {
        return ApiResponse.success(
            PaymentResponse.from(paymentUseCase.getByOrderId(orderId))
        );
    }

    @PostMapping("/callback")
    public ApiResponse<PaymentResponse> callback(@Valid @RequestBody PaymentCallbackRequest request) {
        return ApiResponse.success(
            PaymentResponse.from(paymentUseCase.handleCallback(request))
        );
    }
}