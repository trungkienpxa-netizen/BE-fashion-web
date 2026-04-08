package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.Cart;
import edu.thanglong.domain.repository.CartRepository;
import edu.thanglong.infrastructure.mapper.CartMapper;
import edu.thanglong.infrastructure.repository.mongo.CartMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final CartMongoRepository mongo;
    private final CartMapper mapper;

    @Override
    public Cart save(Cart cart) {
        return mapper.toDomain(mongo.save(mapper.toEntity(cart)));
    }

    @Override
    public Optional<Cart> findByUserId(String userId) {
        return mongo.findByUserId(userId).map(mapper::toDomain);
    }

    @Override
    public void deleteByUserId(String userId) {
        mongo.deleteByUserId(userId);
    }
}