package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.Review;
import edu.thanglong.domain.repository.ReviewRepository;
import edu.thanglong.infrastructure.mapper.ReviewMapper;
import edu.thanglong.infrastructure.repository.mongo.ReviewMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewMongoRepository mongo;
    private final ReviewMapper mapper;

    @Override
    public Review save(Review review) {
        return mapper.toDomain(mongo.save(mapper.toEntity(review)));
    }

    @Override
    public Optional<Review> findById(String id) {
        return mongo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Review> findByProductId(String productId) {
        return mongo.findByProductIdOrderByCreatedAtDesc(productId)
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Review> findByUserId(String userId) {
        return mongo.findByUserId(userId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByUserIdAndOrderId(String userId, String orderId) {
        return mongo.existsByUserIdAndOrderId(userId, orderId);
    }

    @Override
    public boolean existsByUserIdAndProductId(String userId, String productId) {
        return mongo.existsByUserIdAndProductId(userId, productId);
    }

    @Override
    public void deleteById(String id) {
        mongo.deleteById(id);
    }
}