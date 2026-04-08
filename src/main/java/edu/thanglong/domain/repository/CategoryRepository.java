package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(String id);
    Optional<Category> findBySlug(String slug);
    List<Category> findAll();
    List<Category> findRootCategories();
    List<Category> findByParentId(String parentId);
    boolean existsBySlug(String slug);
    void deleteById(String id);
}