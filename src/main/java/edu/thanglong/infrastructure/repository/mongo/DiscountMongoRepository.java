package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.domain.model.Discount;
import edu.thanglong.infrastructure.entity.DiscountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface DiscountMongoRepository extends MongoRepository<DiscountEntity, String> {
    Optional<DiscountEntity> findByCode(String code);
    List<DiscountEntity> findByStatus(Discount.DiscountStatus status);
}