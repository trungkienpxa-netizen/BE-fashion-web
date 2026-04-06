package edu.thanglong.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResetPasswordRequest {
    @NotBlank @Email
    private String email;

    @NotBlank
    private String otp;

    @NotBlank @Size(min = 6)
    private String newPassword;
}