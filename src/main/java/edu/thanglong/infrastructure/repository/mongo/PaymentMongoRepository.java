package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.infrastructure.entity.PaymentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PaymentMongoRepository extends MongoRepository<PaymentEntity, String> {
    Optional<PaymentEntity> findByOrderId(String orderId);
    Optional<PaymentEntity> findByTransactionId(String transactionId);
}