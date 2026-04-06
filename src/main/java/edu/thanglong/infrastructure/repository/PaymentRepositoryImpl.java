package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.Payment;
import edu.thanglong.domain.repository.PaymentRepository;
import edu.thanglong.infrastructure.mapper.PaymentMapper;
import edu.thanglong.infrastructure.repository.mongo.PaymentMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentMongoRepository mongo;
    private final PaymentMapper mapper;

    @Override
    public Payment save(Payment payment) {
        return mapper.toDomain(mongo.save(mapper.toEntity(payment)));
    }

    @Override
    public Optional<Payment> findById(String id) {
        return mongo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByOrderId(String orderId) {
        return mongo.findByOrderId(orderId).map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByTransactionId(String transactionId) {
        return mongo.findByTransactionId(transactionId).map(mapper::toDomain);
    }
}