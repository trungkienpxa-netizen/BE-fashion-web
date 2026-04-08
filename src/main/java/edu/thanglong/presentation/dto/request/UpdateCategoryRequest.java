package edu.thanglong.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCategoryRequest {
    private String name;
    private String parentId;
}