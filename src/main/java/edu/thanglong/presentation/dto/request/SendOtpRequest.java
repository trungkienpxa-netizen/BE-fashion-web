package edu.thanglong.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SendOtpRequest {
    @NotBlank @Email
    private String email;
}