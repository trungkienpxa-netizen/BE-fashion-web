package edu.thanglong.presentation.dto.response;

import edu.thanglong.domain.model.Review;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter @Builder
public class ReviewResponse {
    private String id;
    private String userId;
    private String productId;
    private String orderId;
    private int rating;
    private String comment;
    private boolean verifiedPurchase;
    private LocalDateTime createdAt;

    public static ReviewResponse from(Review r) {
        return ReviewResponse.builder()
                .id(r.getId())
                .userId(r.getUserId())
                .productId(r.getProductId())
                .orderId(r.getOrderId())
                .rating(r.getRating())
                .comment(r.getComment())
                .verifiedPurchase(r.isVerifiedPurchase())
                .createdAt(r.getCreatedAt())
                .build();
    }
}