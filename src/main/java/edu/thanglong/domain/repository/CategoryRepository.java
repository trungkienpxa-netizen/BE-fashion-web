package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.*;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(String id);
    List<Category> findAll();
    List<Category> findByParentId(String parentId);
    void deleteById(String id);
}