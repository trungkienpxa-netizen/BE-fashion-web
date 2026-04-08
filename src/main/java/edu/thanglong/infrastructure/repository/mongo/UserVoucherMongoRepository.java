package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.infrastructure.entity.UserVoucherEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface UserVoucherMongoRepository extends MongoRepository<UserVoucherEntity, String> {
    List<UserVoucherEntity> findByUserId(String userId);
    Optional<UserVoucherEntity> findByUserIdAndDiscountId(String userId, String discountId);
    boolean existsByUserIdAndDiscountId(String userId, String discountId);
}