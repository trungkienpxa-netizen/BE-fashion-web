package edu.thanglong.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class ValidateDiscountRequest {
    @NotBlank  private String code;
    @NotNull   private BigDecimal orderTotal;
}