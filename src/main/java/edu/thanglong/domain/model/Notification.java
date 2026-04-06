package edu.thanglong.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Notification {

    private String id;
    private String userId;
    private NotificationType type;
    private String title;
    private String content;
    private boolean read;
    private LocalDateTime createdAt;

    public enum NotificationType { ORDER_UPDATE, PROMOTION, SYSTEM }
}