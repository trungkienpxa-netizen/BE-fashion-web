package edu.thanglong.presentation.dto.request;

import lombok.Data;

@Data
public class NotificationRequest {
    private String userId;
    private String title;
    private String message;
    private String type; // BOOKING, PAYMENT, SYSTEM, PROMOTION
}