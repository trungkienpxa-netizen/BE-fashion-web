package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.Category;
import edu.thanglong.domain.repository.CategoryRepository;
import edu.thanglong.infrastructure.mapper.CategoryMapper;
import edu.thanglong.infrastructure.repository.mongo.CategoryMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryMongoRepository mongo;
    private final CategoryMapper mapper;

    @Override
    public Category save(Category category) {
        return mapper.toDomain(mongo.save(mapper.toEntity(category)));
    }

    @Override
    public Optional<Category> findById(String id) {
        return mongo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        return mongo.findBySlug(slug).map(mapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return mongo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Category> findRootCategories() {
        return mongo.findByParentIdIsNull().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Category> findByParentId(String parentId) {
        return mongo.findByParentId(parentId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsBySlug(String slug) {
        return mongo.existsBySlug(slug);
    }

    @Override
    public void deleteById(String id) {
        mongo.deleteById(id);
    }
}