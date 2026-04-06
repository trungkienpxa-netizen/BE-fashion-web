package edu.thanglong.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChangePasswordRequest {
    @NotBlank
    private String oldPassword;

    @NotBlank @Size(min = 6)
    private String newPassword;
}