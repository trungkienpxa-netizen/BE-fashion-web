package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.ValidateDiscountRequest;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.DiscountValidateResponse;
import edu.thanglong.usecase.discount.DiscountUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountUseCase discountUseCase;

    @PostMapping("/validate")
    public ApiResponse<DiscountValidateResponse> validate(
            Authentication authentication,
            @Valid @RequestBody ValidateDiscountRequest request) {
        return ApiResponse.success(
            discountUseCase.validate((String) authentication.getPrincipal(), request)
        );
    }
}