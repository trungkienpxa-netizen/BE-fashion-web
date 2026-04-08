package edu.thanglong.presentation.dto.response;

import edu.thanglong.domain.model.Category;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CategoryResponse {
    private String id;
    private String name;
    private String slug;
    private String parentId;

    public static CategoryResponse from(Category c) {
        return CategoryResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .slug(c.getSlug())
                .parentId(c.getParentId())
                .build();
    }
}