package edu.thanglong.infrastructure.mapper;

import edu.thanglong.domain.model.Discount;
import edu.thanglong.infrastructure.entity.DiscountEntity;
import org.springframework.stereotype.Component;

@Component
public class DiscountMapper {

    public Discount toDomain(DiscountEntity e) {
        if (e == null) return null;
        return Discount.builder()
                .id(e.getId())
                .code(e.getCode())
                .type(e.getType())
                .value(e.getValue())
                .maxDiscountAmount(e.getMaxDiscountAmount())
                .minOrderValue(e.getMinOrderValue())
                .usageLimit(e.getUsageLimit())
                .usedCount(e.getUsedCount())
                .firstOrderOnly(e.isFirstOrderOnly())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .status(e.getStatus())
                .build();
    }

    public DiscountEntity toEntity(Discount d) {
        if (d == null) return null;
        return DiscountEntity.builder()
                .id(d.getId())
                .code(d.getCode())
                .type(d.getType())
                .value(d.getValue())
                .maxDiscountAmount(d.getMaxDiscountAmount())
                .minOrderValue(d.getMinOrderValue())
                .usageLimit(d.getUsageLimit())
                .usedCount(d.getUsedCount())
                .firstOrderOnly(d.isFirstOrderOnly())
                .startDate(d.getStartDate())
                .endDate(d.getEndDate())
                .status(d.getStatus())
                .build();
    }
}