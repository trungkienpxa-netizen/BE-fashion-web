package edu.thanglong.infrastructure.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "refresh_tokens")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class RefreshTokenEntity {
    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed(unique = true)
    private String token;

    private LocalDateTime expiredAt;
    private boolean revoked;
}