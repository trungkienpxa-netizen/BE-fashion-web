package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.ProductResponse;
import edu.thanglong.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll() {
        return ApiResponse.success(
            productUseCase.getAll().stream().map(ProductResponse::from).toList()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable String id) {
        return ApiResponse.success(ProductResponse.from(productUseCase.getById(id)));
    }

    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> search(@RequestParam String keyword) {
        return ApiResponse.success(
            productUseCase.search(keyword).stream().map(ProductResponse::from).toList()
        );
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ProductResponse>> getByCategory(@PathVariable String categoryId) {
        return ApiResponse.success(
            productUseCase.getByCategoryId(categoryId).stream().map(ProductResponse::from).toList()
        );
    }
}