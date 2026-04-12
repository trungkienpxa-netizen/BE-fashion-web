package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.infrastructure.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    long countByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}