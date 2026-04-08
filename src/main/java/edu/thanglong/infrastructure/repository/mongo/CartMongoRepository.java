package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.infrastructure.entity.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface CartMongoRepository extends MongoRepository<CartEntity, String> {
    Optional<CartEntity> findByUserId(String userId);
    void deleteByUserId(String userId);
}