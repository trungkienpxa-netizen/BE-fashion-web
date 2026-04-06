package edu.thanglong.infrastructure.mapper;

import edu.thanglong.domain.model.Product;
import edu.thanglong.infrastructure.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(ProductEntity e) {
        if (e == null) return null;
        return Product.builder()
                .id(e.getId())
                .categoryId(e.getCategoryId())
                .name(e.getName())
                .description(e.getDescription())
                .basePrice(e.getBasePrice())
                .brand(e.getBrand())
                .variants(e.getVariants())
                .styles(e.getStyles())
                .occasions(e.getOccasions())
                .bodyShapes(e.getBodyShapes())
                .fitType(e.getFitType())
                .heightRange(e.getHeightRange())
                .weightRange(e.getWeightRange())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    public ProductEntity toEntity(Product d) {
        if (d == null) return null;
        return ProductEntity.builder()
                .id(d.getId())
                .categoryId(d.getCategoryId())
                .name(d.getName())
                .description(d.getDescription())
                .basePrice(d.getBasePrice())
                .brand(d.getBrand())
                .variants(d.getVariants())
                .styles(d.getStyles())
                .occasions(d.getOccasions())
                .bodyShapes(d.getBodyShapes())
                .fitType(d.getFitType())
                .heightRange(d.getHeightRange())
                .weightRange(d.getWeightRange())
                .createdAt(d.getCreatedAt())
                .updatedAt(d.getUpdatedAt())
                .build();
    }
}