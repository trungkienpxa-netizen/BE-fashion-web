package edu.thanglong.infrastructure.mapper;

import edu.thanglong.domain.model.Order;
import edu.thanglong.infrastructure.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toDomain(OrderEntity e) {
        if (e == null) return null;
        return Order.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .addressSnapshot(e.getAddressSnapshot())
                .discountId(e.getDiscountId())
                .totalAmount(e.getTotalAmount())
                .discountAmount(e.getDiscountAmount())
                .paymentMethod(e.getPaymentMethod())
                .status(e.getStatus())
                .items(e.getItems())
                .shippingLogs(e.getShippingLogs())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public OrderEntity toEntity(Order d) {
        if (d == null) return null;
        return OrderEntity.builder()
                .id(d.getId())
                .userId(d.getUserId())
                .addressSnapshot(d.getAddressSnapshot())
                .discountId(d.getDiscountId())
                .totalAmount(d.getTotalAmount())
                .discountAmount(d.getDiscountAmount())
                .paymentMethod(d.getPaymentMethod())
                .status(d.getStatus())
                .items(d.getItems())
                .shippingLogs(d.getShippingLogs())
                .createdAt(d.getCreatedAt())
                .build();
    }
}