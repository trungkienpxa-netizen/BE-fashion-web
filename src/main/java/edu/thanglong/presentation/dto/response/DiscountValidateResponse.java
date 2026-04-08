package edu.thanglong.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Getter @Builder
public class DiscountValidateResponse {
    private String discountId;
    private String code;
    private BigDecimal discountAmount;
}