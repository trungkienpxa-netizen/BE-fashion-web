package edu.thanglong.usecase.notification;

import edu.thanglong.domain.model.Notification;
import edu.thanglong.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationUseCaseImpl implements NotificationUseCase {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getMyNotifications(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public long countUnread(String userId) {
        return notificationRepository.countUnreadByUserId(userId);
    }

    @Override
    public void markAllRead(String userId) {
        notificationRepository.markAllReadByUserId(userId);
    }

    @Override
    public Notification send(String userId, Notification.NotificationType type,
                             String title, String content) {
        return notificationRepository.save(Notification.builder()
                .userId(userId)
                .type(type)
                .title(title)
                .content(content)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build());
    }
}