package edu.thanglong.usecase.dashboard;

import edu.thanglong.presentation.dto.response.DashboardResponse;
import edu.thanglong.presentation.dto.response.TopProductResponse;
import java.util.List;

public interface DashboardUseCase {
    DashboardResponse getMonthlyStats();
    List<TopProductResponse> getTopSellingProducts(int limit);
}