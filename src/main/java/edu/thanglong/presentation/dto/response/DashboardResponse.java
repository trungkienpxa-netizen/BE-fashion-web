package edu.thanglong.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Getter @Builder
public class DashboardResponse {
    // Doanh thu
    private BigDecimal totalRevenue;
    private double revenueGrowthPercent;

    // Đơn hàng
    private long totalOrders;
    private double orderGrowthPercent;

    // Khách hàng mới
    private long totalNewCustomers;
    private double customerGrowthPercent;

    // Tháng thống kê
    private int month;
    private int year;
}