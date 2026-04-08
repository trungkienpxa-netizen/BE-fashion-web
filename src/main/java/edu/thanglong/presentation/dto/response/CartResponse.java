package edu.thanglong.presentation.dto.response;

import edu.thanglong.domain.model.Cart;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Builder
public class CartResponse {
    private String id;
    private String userId;
    private List<Cart.Item> items;
    private int totalItems;
    private LocalDateTime updatedAt;

    public static CartResponse from(Cart c) {
        return CartResponse.builder()
                .id(c.getId())
                .userId(c.getUserId())
                .items(c.getItems())
                .totalItems(c.getItems().stream()
                        .mapToInt(Cart.Item::getQuantity).sum())
                .updatedAt(c.getUpdatedAt())
                .build();
    }
}