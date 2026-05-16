package ezstub_backend.controller;

import ezstub_backend.dto.BudgetDTO;
import ezstub_backend.service.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public BudgetDTO createBudget(@RequestBody BudgetDTO dto) {
        return budgetService.createBudget(dto);
    }

    @GetMapping("/user/{userId}")
    public List<BudgetDTO> getBudgets(@PathVariable Long userId) {
        return budgetService.getBudgetsByUser(userId);
    }

    @GetMapping("/user/{userId}/month/{month}")
    public List<BudgetDTO> getBudgetsByMonth(@PathVariable Long userId, @PathVariable String month) {
        return budgetService.getBudgetsByUserAndMonth(userId, month);
    }

    @GetMapping("/{id}")
    public BudgetDTO getBudgetById(@PathVariable Long id) {
        return budgetService.getBudgetById(id);
    }

    @PutMapping("/{id}")
    public BudgetDTO updateBudget(
            @PathVariable Long id,
            @RequestBody BudgetDTO dto
    ) {
        return budgetService.updateBudget(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
    }

}
