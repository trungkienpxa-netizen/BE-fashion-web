package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.Order;
import edu.thanglong.domain.repository.OrderRepository;
import edu.thanglong.infrastructure.mapper.OrderMapper;
import edu.thanglong.infrastructure.repository.mongo.OrderMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMongoRepository mongo;
    private final OrderMapper mapper;

    @Override
    public Order save(Order order) {
        return mapper.toDomain(mongo.save(mapper.toEntity(order)));
    }

    @Override
    public Optional<Order> findById(String id) {
        return mongo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Order> findByUserId(String userId) {
        return mongo.findByUserId(userId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Order> findByStatus(Order.OrderStatus status) {
        return mongo.findByStatus(status).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Order> findAll() {
        return mongo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Order> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to) {
        return mongo.findByCreatedAtBetween(from, to).stream().map(mapper::toDomain).toList();
    }
}