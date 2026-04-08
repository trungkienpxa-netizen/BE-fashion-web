package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.CreateReviewRequest;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.ReviewResponse;
import edu.thanglong.usecase.review.ReviewUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewUseCase reviewUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReviewResponse> create(Authentication authentication,
                                              @Valid @RequestBody CreateReviewRequest request) {
        return ApiResponse.success(ReviewResponse.from(
            reviewUseCase.create((String) authentication.getPrincipal(), request))
        );
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ReviewResponse>> getByProduct(@PathVariable String productId) {
        return ApiResponse.success(
            reviewUseCase.getByProductId(productId).stream()
                .map(ReviewResponse::from).toList()
        );
    }

    @GetMapping("/my")
    public ApiResponse<List<ReviewResponse>> getMyReviews(Authentication authentication) {
        return ApiResponse.success(
            reviewUseCase.getMyReviews((String) authentication.getPrincipal())
                .stream().map(ReviewResponse::from).toList()
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(Authentication authentication,
                                    @PathVariable String id) {
        reviewUseCase.delete((String) authentication.getPrincipal(), id);
        return ApiResponse.success(null);
    }
}