package edu.thanglong.presentation.dto.request;

import edu.thanglong.domain.model.Order;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class PlaceOrderRequest {

    @NotBlank
    private String addressSnapshot;

    @NotNull
    private Order.PaymentMethod paymentMethod;

    @NotEmpty
    private List<OrderItemRequest> items;

    private String discountCode;

    @Getter @Setter
    public static class OrderItemRequest {
        @NotBlank private String productId;
        @NotBlank private String variantSku;
        @Min(1)   private int quantity;
    }
}