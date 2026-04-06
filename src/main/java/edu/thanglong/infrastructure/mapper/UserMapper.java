package edu.thanglong.infrastructure.mapper;

import edu.thanglong.domain.model.User;
import edu.thanglong.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity e) {
        if (e == null) return null;
        return User.builder()
                .id(e.getId())
                .email(e.getEmail())
                .passwordHash(e.getPasswordHash())
                .fullName(e.getFullName())
                .phone(e.getPhone())
                .role(e.getRole())
                .status(e.getStatus())
                .addresses(e.getAddresses())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public UserEntity toEntity(User d) {
        if (d == null) return null;
        return UserEntity.builder()
                .id(d.getId())
                .email(d.getEmail())
                .passwordHash(d.getPasswordHash())
                .fullName(d.getFullName())
                .phone(d.getPhone())
                .role(d.getRole())
                .status(d.getStatus())
                .addresses(d.getAddresses())
                .createdAt(d.getCreatedAt())
                .build();
    }
}