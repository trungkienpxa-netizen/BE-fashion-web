package edu.thanglong.presentation.dto.response;

import edu.thanglong.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter @Builder
public class UserResponse {
    private String id;
    private String email;
    private String fullName;
    private String phone;
    private String role;
    private String status;
    private LocalDateTime createdAt;

    public static UserResponse from(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .email(u.getEmail())
                .fullName(u.getFullName())
                .phone(u.getPhone())
                .role(u.getRole().name())
                .status(u.getStatus().name())
                .createdAt(u.getCreatedAt())
                .build();
    }
}