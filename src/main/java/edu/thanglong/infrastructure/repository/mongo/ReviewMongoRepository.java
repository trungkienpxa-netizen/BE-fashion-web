package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.infrastructure.entity.ReviewEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ReviewMongoRepository extends MongoRepository<ReviewEntity, String> {
    List<ReviewEntity> findByProductId(String productId);
    List<ReviewEntity> findByUserId(String userId);
    boolean existsByUserIdAndOrderId(String userId, String orderId);
    boolean existsByUserIdAndProductId(String userId, String productId);

    @Query("{ 'productId': ?0 }")
    List<ReviewEntity> findByProductIdOrderByCreatedAtDesc(String productId);
}