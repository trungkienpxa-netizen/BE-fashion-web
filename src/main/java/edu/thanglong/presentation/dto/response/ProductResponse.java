package edu.thanglong.presentation.dto.response;

import edu.thanglong.domain.model.Product;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Builder
public class ProductResponse {
    private String id;
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

    public static ProductResponse from(Product p) {
        return ProductResponse.builder()
                .id(p.getId())
                .categoryId(p.getCategoryId())
                .name(p.getName())
                .description(p.getDescription())
                .basePrice(p.getBasePrice())
                .brand(p.getBrand())
                .variants(p.getVariants())
                .styles(p.getStyles())
                .occasions(p.getOccasions())
                .bodyShapes(p.getBodyShapes())
                .fitType(p.getFitType())
                .heightRange(p.getHeightRange())
                .weightRange(p.getWeightRange())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}