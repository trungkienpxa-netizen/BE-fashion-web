package edu.thanglong.infrastructure.mapper;

import edu.thanglong.domain.model.Review;
import edu.thanglong.infrastructure.entity.ReviewEntity;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review toDomain(ReviewEntity e) {
        if (e == null) return null;
        return Review.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .productId(e.getProductId())
                .orderId(e.getOrderId())
                .rating(e.getRating())
                .comment(e.getComment())
                .verifiedPurchase(e.isVerifiedPurchase())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public ReviewEntity toEntity(Review d) {
        if (d == null) return null;
        return ReviewEntity.builder()
                .id(d.getId())
                .userId(d.getUserId())
                .productId(d.getProductId())
                .orderId(d.getOrderId())
                .rating(d.getRating())
                .comment(d.getComment())
                .verifiedPurchase(d.isVerifiedPurchase())
                .createdAt(d.getCreatedAt())
                .build();
    }
}