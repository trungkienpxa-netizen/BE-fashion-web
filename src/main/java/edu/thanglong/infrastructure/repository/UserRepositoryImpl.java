package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.User;
import edu.thanglong.domain.repository.UserRepository;
import edu.thanglong.infrastructure.mapper.UserMapper;
import edu.thanglong.infrastructure.repository.mongo.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMongoRepository mongo;
    private final UserMapper mapper;

    @Override
    public User save(User user) {
        return mapper.toDomain(mongo.save(mapper.toEntity(user)));
    }

    @Override
    public Optional<User> findById(String id) {
        return mongo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return mongo.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return mongo.existsByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return mongo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(String id) {
        mongo.deleteById(id);
    }

    @Override
    public long countByCreatedAtBetween(LocalDateTime from, LocalDateTime to) {
        return mongo.countByCreatedAtBetween(from, to);
    }
}