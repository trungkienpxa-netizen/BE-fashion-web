package edu.thanglong.infrastructure.repository;

import edu.thanglong.domain.model.Notification;
import edu.thanglong.domain.repository.NotificationRepository;
import edu.thanglong.infrastructure.entity.NotificationEntity;
import edu.thanglong.infrastructure.mapper.NotificationMapper;
import edu.thanglong.infrastructure.repository.mongo.NotificationMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationMongoRepository mongo;
    private final NotificationMapper mapper;

    @Override
    public Notification save(Notification notification) {
        return mapper.toDomain(mongo.save(mapper.toEntity(notification)));
    }

    @Override
    public List<Notification> findByUserId(String userId) {
        return mongo.findByUserIdOrderByCreatedAtDesc(userId)
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public long countUnreadByUserId(String userId) {
        return mongo.countByUserIdAndReadFalse(userId);
    }

    @Override
    public void markAllReadByUserId(String userId) {
        List<NotificationEntity> unread = mongo.findByUserIdAndReadFalse(userId);
        unread.forEach(n -> n.setRead(true));
        mongo.saveAll(unread);
    }
}