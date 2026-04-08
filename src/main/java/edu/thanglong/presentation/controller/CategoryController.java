package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.CategoryResponse;
import edu.thanglong.usecase.category.CategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAll() {
        return ApiResponse.success(
            categoryUseCase.getAll().stream().map(CategoryResponse::from).toList()
        );
    }

    @GetMapping("/roots")
    public ApiResponse<List<CategoryResponse>> getRoots() {
        return ApiResponse.success(
            categoryUseCase.getRootCategories().stream().map(CategoryResponse::from).toList()
        );
    }

    @GetMapping("/{id}/children")
    public ApiResponse<List<CategoryResponse>> getChildren(@PathVariable String id) {
        return ApiResponse.success(
            categoryUseCase.getChildren(id).stream().map(CategoryResponse::from).toList()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getById(@PathVariable String id) {
        return ApiResponse.success(CategoryResponse.from(categoryUseCase.getById(id)));
    }
}