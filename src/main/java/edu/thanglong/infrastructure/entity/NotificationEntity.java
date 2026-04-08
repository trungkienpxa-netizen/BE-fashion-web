package edu.thanglong.infrastructure.entity;

import edu.thanglong.domain.model.Notification;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "notifications")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class NotificationEntity {
    @Id private String id;
    @Indexed private String userId;
    private Notification.NotificationType type;
    private String title;
    private String content;
    private boolean read;
    private LocalDateTime createdAt;
}