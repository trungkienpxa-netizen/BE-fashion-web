package edu.thanglong.infrastructure.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "reviews")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ReviewEntity {
    @Id private String id;
    @Indexed private String userId;
    @Indexed private String productId;
    private String orderId;
    private int rating;
    private String comment;
    private boolean verifiedPurchase;
    private LocalDateTime createdAt;
}