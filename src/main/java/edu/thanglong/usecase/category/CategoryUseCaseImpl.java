package edu.thanglong.usecase.category;

import edu.thanglong.domain.exception.BusinessRuleException;
import edu.thanglong.domain.exception.ResourceNotFoundException;
import edu.thanglong.domain.model.Category;
import edu.thanglong.domain.repository.CategoryRepository;
import edu.thanglong.presentation.dto.request.CreateCategoryRequest;
import edu.thanglong.presentation.dto.request.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CategoryUseCaseImpl implements CategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getRootCategories() {
        return categoryRepository.findRootCategories();
    }

    @Override
    public List<Category> getChildren(String parentId) {
        getById(parentId); // validate parent tồn tại
        return categoryRepository.findByParentId(parentId);
    }

    @Override
    public Category getById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }

    @Override
    public Category create(CreateCategoryRequest req) {
        String slug = generateSlug(req.getName());

        if (categoryRepository.existsBySlug(slug))
            throw new BusinessRuleException("Danh mục đã tồn tại: " + req.getName());

        if (req.getParentId() != null)
            getById(req.getParentId()); // validate parent tồn tại

        return categoryRepository.save(Category.builder()
                .name(req.getName())
                .slug(slug)
                .parentId(req.getParentId())
                .build());
    }

    @Override
    public Category update(String id, UpdateCategoryRequest req) {
        Category category = getById(id);
        if (req.getName() != null) {
            category.setName(req.getName());
            category.setSlug(generateSlug(req.getName()));
        }
        if (req.getParentId() != null) {
            if (req.getParentId().equals(id))
                throw new BusinessRuleException("Danh mục không thể là cha của chính nó");
            getById(req.getParentId());
            category.setParentId(req.getParentId());
        }
        return categoryRepository.save(category);
    }

    @Override
    public void delete(String id) {
        getById(id);
        List<Category> children = categoryRepository.findByParentId(id);
        if (!children.isEmpty())
            throw new BusinessRuleException("Không thể xóa danh mục đang có danh mục con");
        categoryRepository.deleteById(id);
    }

    private String generateSlug(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("")
                .toLowerCase()
                .replaceAll("đ", "d")
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .trim();
    }
}