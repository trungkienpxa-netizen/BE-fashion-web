package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.infrastructure.entity.RefreshTokenEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenMongoRepository extends MongoRepository<RefreshTokenEntity, String> {
    Optional<RefreshTokenEntity> findByToken(String token);
    List<RefreshTokenEntity> findByUserId(String userId);
    void deleteByUserId(String userId);
}