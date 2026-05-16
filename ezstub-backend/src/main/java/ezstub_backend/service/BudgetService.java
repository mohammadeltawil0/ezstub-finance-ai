package ezstub_backend.service;

import ezstub_backend.dto.BudgetDTO;

import java.util.List;

public interface BudgetService {

    BudgetDTO createBudget(BudgetDTO dto);

    List<BudgetDTO> getBudgetsByUser(Long userId);

    List<BudgetDTO> getBudgetsByUserAndMonth(Long userId, String month);

    BudgetDTO getBudgetById(Long id);

    BudgetDTO updateBudget(Long id, BudgetDTO dto);

    void deleteBudget(Long id);
}
