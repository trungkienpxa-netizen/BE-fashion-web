package edu.thanglong.usecase.category;


import edu.thanglong.domain.model.Category;
import edu.thanglong.presentation.dto.request.CreateCategoryRequest;
import edu.thanglong.presentation.dto.request.UpdateCategoryRequest;
import java.util.List;

public interface CategoryUseCase {
    List<Category> getAll();
    List<Category> getRootCategories();
    List<Category> getChildren(String parentId);
    Category getById(String id);
    Category create(CreateCategoryRequest request);
    Category update(String id, UpdateCategoryRequest request);
    void delete(String id);
}