package edu.thanglong.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddToCartRequest {
    @NotBlank private String productId;
    @NotBlank private String variantSku;
    @Min(1)   private int quantity;
}