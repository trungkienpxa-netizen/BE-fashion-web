package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.RefreshToken;
import edu.thanglong.domain.repository.RefreshTokenRepository;
import edu.thanglong.infrastructure.entity.RefreshTokenEntity;
import edu.thanglong.infrastructure.repository.mongo.RefreshTokenMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenMongoRepository mongo;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenEntity entity = RefreshTokenEntity.builder()
                .id(refreshToken.getId())
                .userId(refreshToken.getUserId())
                .token(refreshToken.getToken())
                .expiredAt(refreshToken.getExpiredAt())
                .revoked(refreshToken.isRevoked())
                .build();
        RefreshTokenEntity saved = mongo.save(entity);
        refreshToken.setId(saved.getId());
        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return mongo.findByToken(token).map(e -> RefreshToken.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .token(e.getToken())
                .expiredAt(e.getExpiredAt())
                .revoked(e.isRevoked())
                .build());
    }

    @Override
    public void revokeAllByUserId(String userId) {
        mongo.findByUserId(userId).forEach(e -> {
            e.setRevoked(true);
            mongo.save(e);
        });
    }

    @Override
    public void deleteByUserId(String userId) {
        mongo.deleteByUserId(userId);
    }
}