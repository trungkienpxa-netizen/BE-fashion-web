package edu.thanglong.infrastructure.repository.mongo;

import edu.thanglong.domain.model.Order;
import edu.thanglong.infrastructure.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderMongoRepository extends MongoRepository<OrderEntity, String> {
    List<OrderEntity> findByUserId(String userId);
    List<OrderEntity> findByStatus(Order.OrderStatus status);
}