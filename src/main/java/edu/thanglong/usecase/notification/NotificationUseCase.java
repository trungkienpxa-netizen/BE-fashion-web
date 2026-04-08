package edu.thanglong.usecase.notification;

import edu.thanglong.domain.model.Notification;
import java.util.List;

public interface NotificationUseCase {
    List<Notification> getMyNotifications(String userId);
    long countUnread(String userId);
    void markAllRead(String userId);
    Notification send(String userId, Notification.NotificationType type,
                      String title, String content);
}