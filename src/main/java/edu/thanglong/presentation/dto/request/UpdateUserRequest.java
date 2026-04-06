package edu.thanglong.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserRequest {
    private String fullName;
    private String phone;
}