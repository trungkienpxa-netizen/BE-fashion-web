package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.infrastructure.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryMongoRepository extends MongoRepository<CategoryEntity, String> {
    List<CategoryEntity> findByParentId(String parentId);
    List<CategoryEntity> findByParentIdIsNull();
    Optional<CategoryEntity> findBySlug(String slug);
    boolean existsBySlug(String slug);
}