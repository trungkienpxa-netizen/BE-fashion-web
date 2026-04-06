package edu.thanglong.presentation.dto.request;

import lombok.Data;

@Data
public class GoogleLoginRequest {
    private String tokenGoogle;
    private String idToken;
    private String phoneNumber;
}