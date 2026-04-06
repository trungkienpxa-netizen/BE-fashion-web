package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.domain.model.OtpToken;
import edu.thanglong.infrastructure.entity.OtpTokenEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface OtpTokenMongoRepository extends MongoRepository<OtpTokenEntity, String> {
    Optional<OtpTokenEntity> findTopByEmailAndTypeOrderByExpiredAtDesc(String email, OtpToken.OtpType type);
    void deleteByEmailAndType(String email, OtpToken.OtpType type);
}