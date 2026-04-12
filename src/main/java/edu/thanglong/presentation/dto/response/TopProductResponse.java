package edu.thanglong.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Getter @Builder
public class TopProductResponse {
    private String productId;
    private String productName;
    private long totalSold;
    private long totalOrders;
    private String imageUrl;
    private BigDecimal basePrice;
}