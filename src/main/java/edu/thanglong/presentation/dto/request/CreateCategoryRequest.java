package edu.thanglong.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCategoryRequest {
    @NotBlank
    private String name;
    private String parentId;
}