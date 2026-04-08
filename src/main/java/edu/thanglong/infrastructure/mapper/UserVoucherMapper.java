package edu.thanglong.infrastructure.mapper;

import edu.thanglong.domain.model.UserVoucher;
import edu.thanglong.infrastructure.entity.UserVoucherEntity;
import org.springframework.stereotype.Component;

@Component
public class UserVoucherMapper {

    public UserVoucher toDomain(UserVoucherEntity e) {
        if (e == null) return null;
        return UserVoucher.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .discountId(e.getDiscountId())
                .used(e.isUsed())
                .savedAt(e.getSavedAt())
                .usedAt(e.getUsedAt())
                .build();
    }

    public UserVoucherEntity toEntity(UserVoucher d) {
        if (d == null) return null;
        return UserVoucherEntity.builder()
                .id(d.getId())
                .userId(d.getUserId())
                .discountId(d.getDiscountId())
                .used(d.isUsed())
                .savedAt(d.getSavedAt())
                .usedAt(d.getUsedAt())
                .build();
    }
}