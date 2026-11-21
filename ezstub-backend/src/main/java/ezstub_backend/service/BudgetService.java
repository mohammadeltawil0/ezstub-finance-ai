package ezstub_backend.service;

import ezstub_backend.model.User;
import ezstub_backend.payload.apiResponses.PageResponse;
import ezstub_backend.payload.modelDTOs.BudgetDTO;

import java.util.List;

public interface BudgetService {

    BudgetDTO createBudget(BudgetDTO budgetDTO, User user);

    PageResponse<BudgetDTO> getAllBudgets(User user, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<BudgetDTO> getBudgetsByUser(Long userId);

    List<BudgetDTO> getBudgetsByUserAndMonth(Long userId, String month);

    BudgetDTO getBudgetById(Long id);

    BudgetDTO updateBudget(Long id, BudgetDTO dto);

    void deleteBudget(Long id);
}
