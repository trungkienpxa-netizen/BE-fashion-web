package edu.thanglong.infrastructure.mapper;

import edu.thanglong.domain.model.OtpToken;
import edu.thanglong.infrastructure.entity.OtpTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class OtpTokenMapper {

    public OtpToken toDomain(OtpTokenEntity e) {
        if (e == null) return null;
        return OtpToken.builder()
                .id(e.getId())
                .email(e.getEmail())
                .otp(e.getOtp())
                .type(e.getType())
                .expiredAt(e.getExpiredAt())
                .used(e.isUsed())
                .build();
    }

    public OtpTokenEntity toEntity(OtpToken d) {
        if (d == null) return null;
        return OtpTokenEntity.builder()
                .id(d.getId())
                .email(d.getEmail())
                .otp(d.getOtp())
                .type(d.getType())
                .expiredAt(d.getExpiredAt())
                .used(d.isUsed())
                .build();
    }
}