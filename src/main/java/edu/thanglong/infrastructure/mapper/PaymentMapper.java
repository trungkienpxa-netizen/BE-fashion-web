package edu.thanglong.infrastructure.mapper;

import edu.thanglong.domain.model.Payment;
import edu.thanglong.infrastructure.entity.PaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toDomain(PaymentEntity e) {
        if (e == null) return null;
        return Payment.builder()
                .id(e.getId())
                .orderId(e.getOrderId())
                .amount(e.getAmount())
                .method(e.getMethod())
                .status(e.getStatus())
                .transactionId(e.getTransactionId())
                .paidAt(e.getPaidAt())
                .build();
    }

    public PaymentEntity toEntity(Payment d) {
        if (d == null) return null;
        return PaymentEntity.builder()
                .id(d.getId())
                .orderId(d.getOrderId())
                .amount(d.getAmount())
                .method(d.getMethod())
                .status(d.getStatus())
                .transactionId(d.getTransactionId())
                .paidAt(d.getPaidAt())
                .build();
    }
}