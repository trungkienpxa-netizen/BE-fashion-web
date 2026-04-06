package edu.thanglong.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaymentCallbackRequest {
    @NotBlank private String orderId;
    @NotBlank private String transactionId;
    private boolean success;
}