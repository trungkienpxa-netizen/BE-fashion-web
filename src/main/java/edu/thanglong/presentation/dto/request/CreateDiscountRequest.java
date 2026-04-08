package edu.thanglong.presentation.dto.request;

import edu.thanglong.domain.model.Discount;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class CreateDiscountRequest {
    @NotBlank  private String code;
    @NotNull   private Discount.DiscountType type;
    @NotNull @Positive private BigDecimal value;
               private BigDecimal maxDiscountAmount;
    @NotNull   private BigDecimal minOrderValue;
    @Min(1)    private int usageLimit;
               private boolean firstOrderOnly;
    @NotNull   private LocalDate startDate;
    @NotNull   private LocalDate endDate;
}