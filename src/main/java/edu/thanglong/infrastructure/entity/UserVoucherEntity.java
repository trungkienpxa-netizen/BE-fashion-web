package edu.thanglong.infrastructure.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "user_vouchers")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserVoucherEntity {
    @Id private String id;
    @Indexed private String userId;
    private String discountId;
    private boolean used;
    private LocalDateTime savedAt;
    private LocalDateTime usedAt;
}