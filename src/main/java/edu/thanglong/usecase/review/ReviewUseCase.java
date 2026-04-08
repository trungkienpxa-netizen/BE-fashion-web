package edu.thanglong.usecase.review;

import edu.thanglong.domain.model.Review;
import edu.thanglong.presentation.dto.request.CreateReviewRequest;
import java.util.List;

public interface ReviewUseCase {
    Review create(String userId, CreateReviewRequest request);
    List<Review> getByProductId(String productId);
    List<Review> getMyReviews(String userId);
    void delete(String userId, String reviewId);
}