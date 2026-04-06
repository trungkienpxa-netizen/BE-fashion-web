package edu.thanglong.usecase.order;

import edu.thanglong.domain.exception.BusinessRuleException;
import edu.thanglong.domain.exception.ResourceNotFoundException;
import edu.thanglong.domain.exception.UnauthorizedException;
import edu.thanglong.domain.model.Discount;
import edu.thanglong.domain.model.Order;
import edu.thanglong.domain.model.Payment;
import edu.thanglong.domain.model.Product;
import edu.thanglong.domain.repository.*;
import edu.thanglong.presentation.dto.request.PlaceOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderUseCaseImpl implements OrderUseCase {

    private final OrderRepository   orderRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final DiscountRepository discountRepository;
    private final UserRepository    userRepository;

    @Override
    public Order placeOrder(String userId, PlaceOrderRequest req) {
        // Validate user tồn tại
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        // Build order items & tính subtotal
        List<Order.Item> items = req.getItems().stream().map(itemReq -> {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", itemReq.getProductId()));

            Product.Variant variant = product.getVariants().stream()
                    .filter(v -> v.getSku().equals(itemReq.getVariantSku()))
                    .findFirst()
                    .orElseThrow(() -> new BusinessRuleException(
                        "Variant không tồn tại: " + itemReq.getVariantSku()));

            if (variant.getStockQuantity() < itemReq.getQuantity())
                throw new BusinessRuleException(
                    "Sản phẩm " + product.getName() + " không đủ số lượng trong kho");

            return Order.Item.builder()
                    .variantSku(variant.getSku())
                    .productName(product.getName())
                    .variantInfo(variant.getColor() + " / " + variant.getSize())
                    .quantity(itemReq.getQuantity())
                    .priceAtPurchase(variant.getPrice())
                    .build();
        }).toList();

        BigDecimal subtotal = items.stream()
                .map(i -> i.getPriceAtPurchase().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tính discount nếu có
        BigDecimal discountAmount = BigDecimal.ZERO;
        String discountId = null;

        if (req.getDiscountCode() != null && !req.getDiscountCode().isBlank()) {
            Discount discount = discountRepository.findByCode(req.getDiscountCode())
                    .orElseThrow(() -> new BusinessRuleException(
                        "Mã giảm giá không tồn tại: " + req.getDiscountCode()));

            boolean isFirstOrder = orderRepository.findByUserId(userId).isEmpty();
            if (!discount.isValid(subtotal, isFirstOrder))
                throw new BusinessRuleException("Mã giảm giá không hợp lệ hoặc không đủ điều kiện");

            discountAmount = discount.calculateDiscount(subtotal);
            discountId = discount.getId();

            discount.setUsedCount(discount.getUsedCount() + 1);
            discountRepository.save(discount);
        }

        BigDecimal totalAmount = subtotal.subtract(discountAmount);

        // Tạo order
        Order order = Order.builder()
                .userId(userId)
                .addressSnapshot(req.getAddressSnapshot())
                .discountId(discountId)
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .paymentMethod(req.getPaymentMethod())
                .status(Order.OrderStatus.PENDING)
                .items(items)
                .shippingLogs(List.of())
                .createdAt(LocalDateTime.now())
                .build();

        Order saved = orderRepository.save(order);

        // Tạo payment record
        paymentRepository.save(Payment.builder()
                .orderId(saved.getId())
                .amount(totalAmount)
                .method(req.getPaymentMethod())
                .status(Payment.PaymentStatus.PENDING)
                .build());

        return saved;
    }

    @Override
    public Order getById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
    }

    @Override
    public List<Order> getMyOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order cancelOrder(String userId, String orderId) {
        Order order = getById(orderId);

        if (!order.getUserId().equals(userId))
            throw new UnauthorizedException("Bạn không có quyền hủy đơn hàng này");

        order.cancel();  // business logic trong domain model
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateStatus(String orderId, Order.OrderStatus newStatus) {
        Order order = getById(orderId);

        if (order.getStatus() == Order.OrderStatus.CANCELLED)
            throw new BusinessRuleException("Không thể cập nhật đơn hàng đã hủy");
        if (order.getStatus() == Order.OrderStatus.DELIVERED)
            throw new BusinessRuleException("Đơn hàng đã giao không thể thay đổi trạng thái");

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}