package edu.thanglong.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Order {

    private String id;
    private String userId;
    private String addressSnapshot;    // JSON snapshot địa chỉ lúc đặt
    private String discountId;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private LocalDateTime createdAt;

    private List<Item> items;
    private List<ShippingLog> shippingLogs;

    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED
    }

    public enum PaymentMethod {
        COD, BANKING, MOMO, VISA
    }

    @Getter @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Item {
        private String variantSku;
        private String productName;    // snapshot
        private String variantInfo;    // snapshot "Đỏ / XL"
        private int quantity;
        private BigDecimal priceAtPurchase;
    }

    @Getter @Setter @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ShippingLog {
        private String trackingNumber;
        private String carrierName;
        private String currentStatus;
        private LocalDateTime updatedAt;
    }

    // Business logic
    public boolean isCancellable() {
        return status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (!isCancellable())
            throw new IllegalStateException("Order cannot be cancelled at status: " + status);
        this.status = OrderStatus.CANCELLED;
    }
}