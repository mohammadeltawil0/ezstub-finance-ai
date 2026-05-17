package ezstub_backend.controller;


import ezstub_backend.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    // Get total spending per month
    @GetMapping("/spending/user/{userId}/month/{month}")
    public Map<String, Double> getTotalSpending(@PathVariable Long userId, @PathVariable String month) {
        return analyticsService.getTotalSpending(userId, month);
    }

    // Get spending by category
    @GetMapping("/categories/user/{userId}/month/{month}")
    public Map<String, Double> getSpendingByCategory(@PathVariable Long userId, @PathVariable String month) {
        return analyticsService.getSpendingByCategory(userId, month);
    }

    // Get budget summary
    @GetMapping("/budget-summary/user/{userId}/month/{month}")
    public Map<String, Object> getBudgetSummary(@PathVariable Long userId, @PathVariable String month) {
        return analyticsService.getBudgetSummary(userId, month);
    }

}
