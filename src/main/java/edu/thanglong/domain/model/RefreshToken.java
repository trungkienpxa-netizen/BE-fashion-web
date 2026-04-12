package edu.thanglong.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class RefreshToken {
    private String id;
    private String userId;
    private String token;
    private LocalDateTime expiredAt;
    private boolean revoked;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }
}