package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.Discount;
import edu.thanglong.domain.repository.DiscountRepository;
import edu.thanglong.infrastructure.mapper.DiscountMapper;
import edu.thanglong.infrastructure.repository.mongo.DiscountMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DiscountRepositoryImpl implements DiscountRepository {

    private final DiscountMongoRepository mongo;
    private final DiscountMapper mapper;

    @Override
    public Discount save(Discount discount) {
        return mapper.toDomain(mongo.save(mapper.toEntity(discount)));
    }

    @Override
    public Optional<Discount> findById(String id) {
        return mongo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Discount> findByCode(String code) {
        return mongo.findByCode(code).map(mapper::toDomain);
    }

    @Override
    public List<Discount> findAllActive() {
        return mongo.findByStatus(Discount.DiscountStatus.ACTIVE).stream()
                .map(mapper::toDomain).toList();
    }
}