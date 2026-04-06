package edu.thanglong.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Review {

    private String id;
    private String userId;
    private String productId;
    private String orderId;
    private int rating;                // 1–5
    private String comment;
    private boolean verifiedPurchase;
    private LocalDateTime createdAt;
}