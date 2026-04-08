package edu.thanglong.presentation.dto.response;

import edu.thanglong.domain.model.UserVoucher;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter @Builder
public class UserVoucherResponse {
    private String id;
    private String discountId;
    private boolean used;
    private LocalDateTime savedAt;
    private LocalDateTime usedAt;

    public static UserVoucherResponse from(UserVoucher v) {
        return UserVoucherResponse.builder()
                .id(v.getId())
                .discountId(v.getDiscountId())
                .used(v.isUsed())
                .savedAt(v.getSavedAt())
                .usedAt(v.getUsedAt())
                .build();
    }
}