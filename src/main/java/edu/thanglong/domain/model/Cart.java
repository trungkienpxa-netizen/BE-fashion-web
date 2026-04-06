package edu.thanglong.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Cart {

    private String id;
    private String userId;
    @Builder.Default
    private List<Item> items = new ArrayList<>();
    private LocalDateTime updatedAt;

    @Getter @Setter @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Item {
        private String productId;
        private String variantSku;
        private int quantity;
    }

    // Business logic
    public void addItem(Item incoming) {
        items.stream()
            .filter(i -> i.getVariantSku().equals(incoming.getVariantSku()))
            .findFirst()
            .ifPresentOrElse(
                existing -> existing.setQuantity(existing.getQuantity() + incoming.getQuantity()),
                () -> items.add(incoming)
            );
    }

    public void removeItem(String variantSku) {
        items.removeIf(i -> i.getVariantSku().equals(variantSku));
    }

    public void clear() {
        items.clear();
    }
}