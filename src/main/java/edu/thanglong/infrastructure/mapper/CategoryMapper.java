package edu.thanglong.infrastructure.mapper;

import edu.thanglong.domain.model.Category;
import edu.thanglong.infrastructure.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toDomain(CategoryEntity e) {
        if (e == null) return null;
        return Category.builder()
                .id(e.getId())
                .name(e.getName())
                .slug(e.getSlug())
                .parentId(e.getParentId())
                .build();
    }

    public CategoryEntity toEntity(Category d) {
        if (d == null) return null;
        return CategoryEntity.builder()
                .id(d.getId())
                .name(d.getName())
                .slug(d.getSlug())
                .parentId(d.getParentId())
                .build();
    }
}