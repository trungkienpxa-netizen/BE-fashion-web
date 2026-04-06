package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.CreateProductRequest;
import edu.thanglong.presentation.dto.request.UpdateProductRequest;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.ProductResponse;
import edu.thanglong.usecase.product.ProductUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/staff/products")
@PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
@RequiredArgsConstructor
public class StaffProductController {

    private final ProductUseCase productUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        return ApiResponse.success(ProductResponse.from(productUseCase.create(request)));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(@PathVariable String id,
                                               @RequestBody UpdateProductRequest request) {
        return ApiResponse.success(ProductResponse.from(productUseCase.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        productUseCase.delete(id);
        return ApiResponse.success(null);
    }
}