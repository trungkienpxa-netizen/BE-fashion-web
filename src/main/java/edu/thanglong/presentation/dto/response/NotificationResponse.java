package edu.thanglong.presentation.dto.response;

import edu.thanglong.domain.model.Notification;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter @Builder
public class NotificationResponse {
    private String id;
    private String type;
    private String title;
    private String content;
    private boolean read;
    private LocalDateTime createdAt;

    public static NotificationResponse from(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .type(n.getType().name())
                .title(n.getTitle())
                .content(n.getContent())
                .read(n.isRead())
                .createdAt(n.getCreatedAt())
                .build();
    }
}