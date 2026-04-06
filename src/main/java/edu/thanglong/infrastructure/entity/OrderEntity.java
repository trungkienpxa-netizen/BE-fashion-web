package edu.thanglong.infrastructure.entity;

import edu.thanglong.domain.model.Order;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderEntity {

    @Id
    private String id;

    @Indexed
    private String userId;

    private String addressSnapshot;
    private String discountId;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private Order.PaymentMethod paymentMethod;
    private Order.OrderStatus status;
    private List<Order.Item> items;
    private List<Order.ShippingLog> shippingLogs;
    private LocalDateTime createdAt;
}