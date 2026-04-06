package edu.thanglong.domain.model;


import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Discount {

    private String id;
    private String code;
    private DiscountType type;
    private BigDecimal value;
    private BigDecimal maxDiscountAmount;
    private BigDecimal minOrderValue;
    private int usageLimit;
    private int usedCount;
    private boolean firstOrderOnly;
    private LocalDate startDate;
    private LocalDate endDate;
    private DiscountStatus status;

    public enum DiscountType   { PERCENTAGE, FIXED_AMOUNT }
    public enum DiscountStatus { ACTIVE, EXPIRED, DISABLED }

    // Business logic
    public boolean isValid(BigDecimal orderTotal, boolean isFirstOrder) {
        if (status != DiscountStatus.ACTIVE)              return false;
        if (LocalDate.now().isBefore(startDate))          return false;
        if (LocalDate.now().isAfter(endDate))             return false;
        if (usedCount >= usageLimit)                      return false;
        if (orderTotal.compareTo(minOrderValue) < 0)      return false;
        if (firstOrderOnly && !isFirstOrder)              return false;
        return true;
    }

    public BigDecimal calculateDiscount(BigDecimal orderTotal) {
        if (type == DiscountType.FIXED_AMOUNT)
            return value.min(orderTotal);

        BigDecimal calculated = orderTotal.multiply(value)
            .divide(BigDecimal.valueOf(100));
        return maxDiscountAmount != null
            ? calculated.min(maxDiscountAmount)
            : calculated;
    }
}