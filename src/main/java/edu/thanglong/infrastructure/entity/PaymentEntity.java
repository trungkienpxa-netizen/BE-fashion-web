package edu.thanglong.infrastructure.entity;

import edu.thanglong.domain.model.Order;
import edu.thanglong.domain.model.Payment;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "payments")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class PaymentEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String orderId;

    private BigDecimal amount;
    private Order.PaymentMethod method;
    private Payment.PaymentStatus status;

    @Indexed(unique = true, sparse = true)
    private String transactionId;

    private LocalDateTime paidAt;
}