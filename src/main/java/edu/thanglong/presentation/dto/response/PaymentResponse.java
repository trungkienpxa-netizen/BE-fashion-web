package edu.thanglong.presentation.dto.response;

import edu.thanglong.domain.model.Payment;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Builder
public class PaymentResponse {
    private String id;
    private String orderId;
    private BigDecimal amount;
    private String method;
    private String status;
    private String transactionId;
    private LocalDateTime paidAt;

    public static PaymentResponse from(Payment p) {
        return PaymentResponse.builder()
                .id(p.getId())
                .orderId(p.getOrderId())
                .amount(p.getAmount())
                .method(p.getMethod().name())
                .status(p.getStatus().name())
                .transactionId(p.getTransactionId())
                .paidAt(p.getPaidAt())
                .build();
    }
}