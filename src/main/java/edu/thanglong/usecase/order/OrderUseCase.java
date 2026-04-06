package edu.thanglong.usecase.order;

import edu.thanglong.domain.model.Order;
import edu.thanglong.presentation.dto.request.PlaceOrderRequest;
import java.util.List;

public interface OrderUseCase {
    // Tất cả người dùng
    Order placeOrder(String userId, PlaceOrderRequest request);
    Order getById(String id);
    List<Order> getMyOrders(String userId);
    Order cancelOrder(String userId, String orderId);

    // Staff & Admin
    List<Order> getAllOrders();
    Order updateStatus(String orderId, Order.OrderStatus newStatus);
}