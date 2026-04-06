package edu.thanglong.infrastructure.entity;

import edu.thanglong.domain.model.OtpToken;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "otp_tokens")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OtpTokenEntity {

    @Id
    private String id;

    @Indexed
    private String email;

    private String otp;
    private OtpToken.OtpType type;
    private LocalDateTime expiredAt;
    private boolean used;
}