package edu.thanglong.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCartItemRequest {
    @NotBlank private String variantSku;
    @Min(0)   private int quantity;  // 0 = xóa khỏi giỏ
}