package edu.thanglong.infrastructure.entity;

import edu.thanglong.domain.model.Cart;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "carts")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CartEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private List<Cart.Item> items;
    private LocalDateTime updatedAt;
}