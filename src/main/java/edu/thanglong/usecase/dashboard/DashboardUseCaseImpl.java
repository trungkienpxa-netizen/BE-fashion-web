package edu.thanglong.usecase.dashboard;

import edu.thanglong.domain.model.Order;
import edu.thanglong.domain.model.Product;
import edu.thanglong.domain.repository.OrderRepository;
import edu.thanglong.domain.repository.ProductRepository;
import edu.thanglong.domain.repository.UserRepository;
import edu.thanglong.presentation.dto.response.DashboardResponse;
import edu.thanglong.presentation.dto.response.TopProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardUseCaseImpl implements DashboardUseCase {

    private final OrderRepository   orderRepository;
    private final UserRepository    userRepository;
    private final ProductRepository productRepository;

    @Override
    public DashboardResponse getMonthlyStats() {
        LocalDateTime now = LocalDateTime.now();

        // Tháng này
        LocalDateTime thisMonthStart = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime thisMonthEnd   = now;

        // Tháng trước
        LocalDateTime lastMonthStart = thisMonthStart.minusMonths(1);
        LocalDateTime lastMonthEnd   = thisMonthStart.minusSeconds(1);

        // Orders tháng này & tháng trước
        List<Order> thisMonthOrders = orderRepository.findByCreatedAtBetween(thisMonthStart, thisMonthEnd)
                .stream().filter(o -> o.getStatus() != Order.OrderStatus.CANCELLED).toList();
        List<Order> lastMonthOrders = orderRepository.findByCreatedAtBetween(lastMonthStart, lastMonthEnd)
                .stream().filter(o -> o.getStatus() != Order.OrderStatus.CANCELLED).toList();

        // Doanh thu
        BigDecimal thisRevenue = thisMonthOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastRevenue = lastMonthOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Đơn hàng
        long thisOrders = thisMonthOrders.size();
        long lastOrders = lastMonthOrders.size();

        // Khách hàng mới
        long thisCustomers = userRepository.countByCreatedAtBetween(thisMonthStart, thisMonthEnd);
        long lastCustomers = userRepository.countByCreatedAtBetween(lastMonthStart, lastMonthEnd);

        return DashboardResponse.builder()
                // Doanh thu
                .totalRevenue(thisRevenue)
                .revenueGrowthPercent(calcGrowth(lastRevenue, thisRevenue))
                // Đơn hàng
                .totalOrders(thisOrders)
                .orderGrowthPercent(calcGrowth(
                    BigDecimal.valueOf(lastOrders), BigDecimal.valueOf(thisOrders)))
                // Khách hàng
                .totalNewCustomers(thisCustomers)
                .customerGrowthPercent(calcGrowth(
                    BigDecimal.valueOf(lastCustomers), BigDecimal.valueOf(thisCustomers)))
                // Tháng thống kê
                .month(now.getMonthValue())
                .year(now.getYear())
                .build();
    }

    @Override
    public List<TopProductResponse> getTopSellingProducts(int limit) {
        // Lấy tất cả đơn hàng đã hoàn thành
        List<Order> deliveredOrders = orderRepository.findAll().stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.DELIVERED)
                .toList();

        // Tính tổng số lượng bán theo productName (dùng snapshot tên)
        Map<String, long[]> productStats = new HashMap<>();

        deliveredOrders.forEach(order ->
            order.getItems().forEach(item -> {
                String key = item.getProductName();
                productStats.computeIfAbsent(key, k -> new long[]{0, 0});
                productStats.get(key)[0] += item.getQuantity();  // tổng số lượng
                productStats.get(key)[1]++;                       // số đơn hàng
            })
        );

        // Tìm productId theo tên để lấy thông tin đầy đủ
        List<Product> allProducts = productRepository.findAll();
        Map<String, Product> productByName = allProducts.stream()
                .collect(Collectors.toMap(Product::getName, p -> p, (a, b) -> a));

        return productStats.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue()[0], a.getValue()[0]))
                .limit(limit)
                .map(entry -> {
                    Product product = productByName.get(entry.getKey());
                    return TopProductResponse.builder()
                            .productId(product != null ? product.getId() : null)
                            .productName(entry.getKey())
                            .totalSold(entry.getValue()[0])
                            .totalOrders(entry.getValue()[1])
                            .imageUrl(product != null && product.getVariants() != null
                                && !product.getVariants().isEmpty()
                                ? product.getVariants().get(0).getImageUrl() : null)
                            .basePrice(product != null ? product.getBasePrice() : null)
                            .build();
                })
                .toList();
    }

    // Tính % tăng trưởng
    private double calcGrowth(BigDecimal previous, BigDecimal current) {
        if (previous.compareTo(BigDecimal.ZERO) == 0) {
            return current.compareTo(BigDecimal.ZERO) > 0 ? 100.0 : 0.0;
        }
        return current.subtract(previous)
                .divide(previous, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}