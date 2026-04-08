package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Review save(Review review);
    Optional<Review> findById(String id);
    List<Review> findByProductId(String productId);
    List<Review> findByUserId(String userId);
    boolean existsByUserIdAndOrderId(String userId, String orderId);
    boolean existsByUserIdAndProductId(String userId, String productId);
    void deleteById(String id);
}