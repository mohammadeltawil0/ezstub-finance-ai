package ezstub_backend.service;

import ezstub_backend.model.User;
import ezstub_backend.payload.BudgetDTO;

import java.util.List;

public interface BudgetService {

    BudgetDTO createBudget(BudgetDTO budgetDTO, User user);

//    List<BudgetDTO> getBudgetsByUser(Long userId);
//
//    List<BudgetDTO> getBudgetsByUserAndMonth(Long userId, String month);
//
//    BudgetDTO getBudgetById(Long id);
//
//    BudgetDTO updateBudget(Long id, BudgetDTO dto);
//
//    void deleteBudget(Long id);
}
