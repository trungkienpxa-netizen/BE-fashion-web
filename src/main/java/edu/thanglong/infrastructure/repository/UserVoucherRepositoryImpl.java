package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.UserVoucher;
import edu.thanglong.domain.repository.UserVoucherRepository;
import edu.thanglong.infrastructure.mapper.UserVoucherMapper;
import edu.thanglong.infrastructure.repository.mongo.UserVoucherMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserVoucherRepositoryImpl implements UserVoucherRepository {

    private final UserVoucherMongoRepository mongo;
    private final UserVoucherMapper mapper;

    @Override
    public UserVoucher save(UserVoucher userVoucher) {
        return mapper.toDomain(mongo.save(mapper.toEntity(userVoucher)));
    }

    @Override
    public List<UserVoucher> findByUserId(String userId) {
        return mongo.findByUserId(userId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<UserVoucher> findByUserIdAndDiscountId(String userId, String discountId) {
        return mongo.findByUserIdAndDiscountId(userId, discountId).map(mapper::toDomain);
    }

    @Override
    public boolean existsByUserIdAndDiscountId(String userId, String discountId) {
        return mongo.existsByUserIdAndDiscountId(userId, discountId);
    }
}