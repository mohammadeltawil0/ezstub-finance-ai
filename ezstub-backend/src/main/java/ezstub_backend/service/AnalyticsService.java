package ezstub_backend.service;

import java.math.BigDecimal;
import java.util.Map;

public interface AnalyticsService {

    Map<String, BigDecimal> getTotalSpending(Long userId, String month);

    Map<String, BigDecimal> getSpendingByCategory(Long userId, String month);

    Map<String, Object> getBudgetSummary(Long userId, String month);
}
