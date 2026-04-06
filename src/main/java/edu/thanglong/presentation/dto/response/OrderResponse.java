package edu.thanglong.presentation.dto.response;

import edu.thanglong.domain.model.Order;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Builder
public class OrderResponse {
    private String id;
    private String userId;
    private String addressSnapshot;
    private String discountId;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private String paymentMethod;
    private String status;
    private List<Order.Item> items;
    private List<Order.ShippingLog> shippingLogs;
    private LocalDateTime createdAt;

    public static OrderResponse from(Order o) {
        return OrderResponse.builder()
                .id(o.getId())
                .userId(o.getUserId())
                .addressSnapshot(o.getAddressSnapshot())
                .discountId(o.getDiscountId())
                .totalAmount(o.getTotalAmount())
                .discountAmount(o.getDiscountAmount())
                .paymentMethod(o.getPaymentMethod().name())
                .status(o.getStatus().name())
                .items(o.getItems())
                .shippingLogs(o.getShippingLogs())
                .createdAt(o.getCreatedAt())
                .build();
    }
}