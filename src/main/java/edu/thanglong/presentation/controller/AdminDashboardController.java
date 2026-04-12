package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.DashboardResponse;
import edu.thanglong.presentation.dto.response.TopProductResponse;
import edu.thanglong.usecase.dashboard.DashboardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final DashboardUseCase dashboardUseCase;

    @GetMapping("/monthly-stats")
    public ApiResponse<DashboardResponse> getMonthlyStats() {
        return ApiResponse.success(dashboardUseCase.getMonthlyStats());
    }

    @GetMapping("/top-products")
    public ApiResponse<List<TopProductResponse>> getTopProducts(
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.success(dashboardUseCase.getTopSellingProducts(limit));
    }
}