package edu.thanglong.presentation.dto.response;

import edu.thanglong.domain.model.Discount;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Builder
public class DiscountResponse {
    private String id;
    private String code;
    private String type;
    private BigDecimal value;
    private BigDecimal maxDiscountAmount;
    private BigDecimal minOrderValue;
    private int usageLimit;
    private int usedCount;
    private boolean firstOrderOnly;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public static DiscountResponse from(Discount d) {
        return DiscountResponse.builder()
                .id(d.getId())
                .code(d.getCode())
                .type(d.getType().name())
                .value(d.getValue())
                .maxDiscountAmount(d.getMaxDiscountAmount())
                .minOrderValue(d.getMinOrderValue())
                .usageLimit(d.getUsageLimit())
                .usedCount(d.getUsedCount())
                .firstOrderOnly(d.isFirstOrderOnly())
                .startDate(d.getStartDate())
                .endDate(d.getEndDate())
                .status(d.getStatus().name())
                .build();
    }
}