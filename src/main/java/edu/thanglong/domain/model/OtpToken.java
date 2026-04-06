package edu.thanglong.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OtpToken {

    private String id;
    private String email;
    private String otp;
    private OtpType type;
    private LocalDateTime expiredAt;
    private boolean used;

    public enum OtpType { REGISTER, FORGOT_PASSWORD }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }
}