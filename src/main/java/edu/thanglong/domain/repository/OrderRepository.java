package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(String id);
    List<Order> findByUserId(String userId);
    List<Order> findByStatus(Order.OrderStatus status);
    List<Order> findAll();
}