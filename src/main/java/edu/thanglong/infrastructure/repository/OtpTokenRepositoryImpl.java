package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.OtpToken;
import edu.thanglong.domain.repository.OtpTokenRepository;
import edu.thanglong.infrastructure.mapper.OtpTokenMapper;
import edu.thanglong.infrastructure.repository.mongo.OtpTokenMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OtpTokenRepositoryImpl implements OtpTokenRepository {

    private final OtpTokenMongoRepository mongo;
    private final OtpTokenMapper mapper;

    @Override
    public OtpToken save(OtpToken otpToken) {
        return mapper.toDomain(mongo.save(mapper.toEntity(otpToken)));
    }

    @Override
    public Optional<OtpToken> findLatestByEmailAndType(String email, OtpToken.OtpType type) {
        return mongo.findTopByEmailAndTypeOrderByExpiredAtDesc(email, type)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteByEmailAndType(String email, OtpToken.OtpType type) {
        mongo.deleteByEmailAndType(email, type);
    }
}