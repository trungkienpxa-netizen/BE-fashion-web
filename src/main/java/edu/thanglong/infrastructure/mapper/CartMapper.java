package edu.thanglong.infrastructure.mapper;

import edu.thanglong.domain.model.Cart;
import edu.thanglong.infrastructure.entity.CartEntity;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public Cart toDomain(CartEntity e) {
        if (e == null) return null;
        return Cart.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .items(e.getItems() != null ? e.getItems() : new java.util.ArrayList<>())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    public CartEntity toEntity(Cart d) {
        if (d == null) return null;
        return CartEntity.builder()
                .id(d.getId())
                .userId(d.getUserId())
                .items(d.getItems())
                .updatedAt(d.getUpdatedAt())
                .build();
    }
}