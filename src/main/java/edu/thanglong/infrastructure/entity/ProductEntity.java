package edu.thanglong.infrastructure.entity;

import edu.thanglong.domain.model.Product;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "products")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ProductEntity {

    @Id
    private String id;

    @Indexed
    private String categoryId;

    private String name;
    private String description;
    private BigDecimal basePrice;
    private String brand;
    private List<Product.Variant> variants;
    private List<String> styles;
    private List<String> occasions;
    private List<String> bodyShapes;
    private String fitType;
    private String heightRange;
    private String weightRange;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}