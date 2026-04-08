package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.CreateDiscountRequest;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.DiscountResponse;
import edu.thanglong.usecase.discount.DiscountUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/discounts")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminDiscountController {

    private final DiscountUseCase discountUseCase;

    @GetMapping
    public ApiResponse<List<DiscountResponse>> getAll() {
        return ApiResponse.success(
            discountUseCase.getAll().stream().map(DiscountResponse::from).toList()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<DiscountResponse> getById(@PathVariable String id) {
        return ApiResponse.success(DiscountResponse.from(discountUseCase.getById(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DiscountResponse> create(@Valid @RequestBody CreateDiscountRequest request) {
        return ApiResponse.success(DiscountResponse.from(discountUseCase.create(request)));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        discountUseCase.delete(id);
        return ApiResponse.success(null);
    }
}