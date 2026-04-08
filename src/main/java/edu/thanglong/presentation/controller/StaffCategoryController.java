package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.CreateCategoryRequest;
import edu.thanglong.presentation.dto.request.UpdateCategoryRequest;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.CategoryResponse;
import edu.thanglong.usecase.category.CategoryUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/staff/categories")
@PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
@RequiredArgsConstructor
public class StaffCategoryController {

    private final CategoryUseCase categoryUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CategoryResponse> create(@Valid @RequestBody CreateCategoryRequest request) {
        return ApiResponse.success(CategoryResponse.from(categoryUseCase.create(request)));
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> update(@PathVariable String id,
                                                @RequestBody UpdateCategoryRequest request) {
        return ApiResponse.success(CategoryResponse.from(categoryUseCase.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        categoryUseCase.delete(id);
        return ApiResponse.success(null);
    }
}