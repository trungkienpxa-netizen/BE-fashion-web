package edu.thanglong.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateReviewRequest {
    @NotBlank private String productId;
    @NotBlank private String orderId;
    @Min(1) @Max(5) private int rating;
    private String comment;
}