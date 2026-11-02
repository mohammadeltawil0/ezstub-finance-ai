package ezstub_backend.service;

import ezstub_backend.model.User;
import ezstub_backend.payload.modelDTOs.BudgetDTO;
import ezstub_backend.payload.apiResponses.BudgetResponse;

public interface BudgetService {

    BudgetDTO createBudget(BudgetDTO budgetDTO, User user);

    BudgetResponse getAllBudgets(User user, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

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
