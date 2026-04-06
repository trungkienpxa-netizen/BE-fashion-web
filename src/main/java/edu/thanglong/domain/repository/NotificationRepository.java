package edu.thanglong.domain.repository;

import edu.thanglong.domain.model.*;
import java.util.List;

public interface NotificationRepository {
    Notification save(Notification notification);
    List<Notification> findByUserId(String userId);
    long countUnreadByUserId(String userId);
    void markAllReadByUserId(String userId);
}