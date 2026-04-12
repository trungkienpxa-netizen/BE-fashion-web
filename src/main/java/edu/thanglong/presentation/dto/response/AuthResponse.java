package edu.thanglong.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String email;
    private String fullName;
    private String role;
}