package edu.thanglong.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserVoucher {

    private String id;
    private String userId;
    private String discountId;
    private boolean used;
    private LocalDateTime savedAt;
    private LocalDateTime usedAt;
}