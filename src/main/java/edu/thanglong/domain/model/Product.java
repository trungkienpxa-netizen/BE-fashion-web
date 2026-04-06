package edu.thanglong.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Product {

    private String id;
    private String categoryId;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private String brand;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Variants
    private List<Variant> variants;

    // AI metadata — style
    private List<String> styles;       // ["Minimalism", "Streetwear"]
    private List<String> occasions;    // ["Công sở", "Đi tiệc"]

    // AI metadata — body matching
    private List<String> bodyShapes;   // ["Quả lê", "Đồng hồ cát"]
    private String fitType;            // "Oversize", "Slim-fit"
    private String heightRange;        // "160-170cm"
    private String weightRange;        // "50-60kg"

    @Getter @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Variant {
        private String sku;
        private BigDecimal price;
        private int stockQuantity;
        private String color;
        private String size;
        private String imageUrl;
    }
}


