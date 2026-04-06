package edu.thanglong.domain.model;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Category {

    private String id;
    private String name;
    private String slug;
    private String parentId;   // null nếu là root
}