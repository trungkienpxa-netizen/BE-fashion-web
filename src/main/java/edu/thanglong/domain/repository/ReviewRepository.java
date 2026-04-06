package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.*;
import java.util.List;

public interface ReviewRepository {
    Review save(Review review);
    List<Review> findByProductId(String productId);
    List<Review> findByUserId(String userId);
    boolean existsByUserIdAndOrderId(String userId, String orderId);
}