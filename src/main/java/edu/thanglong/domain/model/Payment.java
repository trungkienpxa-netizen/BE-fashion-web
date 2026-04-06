package edu.thanglong.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Payment {

    private String id;
    private String orderId;
    private BigDecimal amount;
    private Order.PaymentMethod method;
    private PaymentStatus status;
    private String transactionId;
    private LocalDateTime paidAt;

    public enum PaymentStatus { PENDING, SUCCESS, FAILED }
}