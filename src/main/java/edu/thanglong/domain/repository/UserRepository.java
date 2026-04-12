package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findAll();
    void deleteById(String id);
    long countByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}