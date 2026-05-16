package ezstub_backend.service.impl;

import ezstub_backend.model.Budget;
import ezstub_backend.model.Transaction;
import ezstub_backend.model.enums.TransactionType;
import ezstub_backend.repository.BudgetRepository;
import ezstub_backend.repository.TransactionRepository;
import ezstub_backend.service.AnalyticsService;
import ezstub_backend.service.BudgetService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    public AnalyticsServiceImpl(TransactionRepository transactionRepository, BudgetRepository budgetRepository) {
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Map<String, Double> getTotalSpending(Long userId, String month) {

        double total = transactionRepository.findByUserId(userId)
                .stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .filter(t -> t.getTransactionDate().toString().startsWith(month))
                .mapToDouble(Transaction::getAmount)
                .sum();

        return Map.of("totalSpending", total);
    }

    @Override
    public Map<String, Double> getSpendingByCategory(Long userId, String month) {

        Map<String, Double> result = new HashMap<>();

        transactionRepository.findByUserId(userId)
                .stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .filter(t -> t.getTransactionDate().toString().startsWith(month))
                .forEach(t -> {
                    String category = t.getExpenseCategory().name();
                    result.put(category, result.getOrDefault(category, 0.0) + t.getAmount());
                });
        return result;
    }

    @Override
    public Map<String, Object> getBudgetSummary(Long userId, String month) {

        Map<String, Double> spentByCategory = getSpendingByCategory(userId, month);

        List<Budget> budgets = budgetRepository.findByUserIdAndMonth(userId, month);

        List<Map<String, Object>> summary = new ArrayList<>();

        for (Budget b : budgets) {
            double spent = spentByCategory.getOrDefault(b.getCategory().name(), 0.0);

            Map<String, Object> item = new HashMap<>();
            item.put("category", b.getCategory().name());
            item.put("budgetLimit", b.getLimitAmount());
            item.put("spent", spent);
            item.put("remaining", b.getLimitAmount() - spent);

            summary.add(item);
        }

        return Map.of("budgetSummary", summary);
    }
}
