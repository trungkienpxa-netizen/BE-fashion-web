package edu.thanglong.presentation.dto.request;

import lombok.Data;

@Data
public class CheckPhoneRequest {
    private String email;
    private String phoneNumber;
}