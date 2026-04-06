package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.infrastructure.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ProductMongoRepository extends MongoRepository<ProductEntity, String> {
    List<ProductEntity> findByCategoryId(String categoryId);
    List<ProductEntity> findByIdIn(List<String> ids);

    @Query("{ $or: [ " +
           "{ 'name': { $regex: ?0, $options: 'i' } }, " +
           "{ 'brand': { $regex: ?0, $options: 'i' } }, " +
           "{ 'description': { $regex: ?0, $options: 'i' } } " +
           "] }")
    List<ProductEntity> searchByKeyword(String keyword);
}