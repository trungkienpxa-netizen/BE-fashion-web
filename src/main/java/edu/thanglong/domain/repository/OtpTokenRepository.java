package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.OtpToken;
import java.util.Optional;

public interface OtpTokenRepository {
    OtpToken save(OtpToken otpToken);
    Optional<OtpToken> findLatestByEmailAndType(String email, OtpToken.OtpType type);
    void deleteByEmailAndType(String email, OtpToken.OtpType type);
}