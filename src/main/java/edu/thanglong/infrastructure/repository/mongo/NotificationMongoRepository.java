package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.infrastructure.entity.NotificationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificationMongoRepository extends MongoRepository<NotificationEntity, String> {
    List<NotificationEntity> findByUserIdOrderByCreatedAtDesc(String userId);
    long countByUserIdAndReadFalse(String userId);
    List<NotificationEntity> findByUserIdAndReadFalse(String userId);
}