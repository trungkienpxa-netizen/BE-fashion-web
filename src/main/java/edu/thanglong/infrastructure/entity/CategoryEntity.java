package edu.thanglong.infrastructure.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CategoryEntity {
    @Id
    private String id;
    private String name;
    private String slug;
    private String parentId;
}