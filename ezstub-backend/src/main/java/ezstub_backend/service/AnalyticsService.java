package ezstub_backend.service;

import java.util.Map;

public interface AnalyticsService {

    Map<String, Double> getTotalSpending(Long userId, String month);

    Map<String, Double> getSpendingByCategory(Long userId, String month);

    Map<String, Object> getBudgetSummary(Long userId, String month);
}
