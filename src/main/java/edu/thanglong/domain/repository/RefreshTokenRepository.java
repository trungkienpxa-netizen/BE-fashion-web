package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
    void revokeAllByUserId(String userId);
    void deleteByUserId(String userId);
}