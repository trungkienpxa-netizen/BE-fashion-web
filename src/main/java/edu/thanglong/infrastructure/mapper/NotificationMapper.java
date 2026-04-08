package edu.thanglong.infrastructure.mapper;

import edu.thanglong.domain.model.Notification;
import edu.thanglong.infrastructure.entity.NotificationEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification toDomain(NotificationEntity e) {
        if (e == null) return null;
        return Notification.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .type(e.getType())
                .title(e.getTitle())
                .content(e.getContent())
                .read(e.isRead())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public NotificationEntity toEntity(Notification d) {
        if (d == null) return null;
        return NotificationEntity.builder()
                .id(d.getId())
                .userId(d.getUserId())
                .type(d.getType())
                .title(d.getTitle())
                .content(d.getContent())
                .read(d.isRead())
                .createdAt(d.getCreatedAt())
                .build();
    }
}