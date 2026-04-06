package edu.thanglong.presentation.dto.request;

import edu.thanglong.domain.model.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateOrderStatusRequest {
    @NotNull
    private Order.OrderStatus status;
}