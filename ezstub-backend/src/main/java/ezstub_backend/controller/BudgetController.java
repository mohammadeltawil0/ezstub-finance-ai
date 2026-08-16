package ezstub_backend.controller;

import ezstub_backend.config.AppConstants;
import ezstub_backend.model.User;
import ezstub_backend.payload.BudgetDTO;
import ezstub_backend.payload.BudgetResponse;
import ezstub_backend.service.BudgetService;
import ezstub_backend.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    AuthUtil authUtil;

    @Autowired
    BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetDTO> createBudget(@Valid @RequestBody BudgetDTO budgetDTO) {
        User user = authUtil.loggedInUser();
        BudgetDTO savedBudgetDTO = budgetService.createBudget(budgetDTO, user);
        return new ResponseEntity<>(savedBudgetDTO, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<BudgetResponse> getBudgets(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                     @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                     @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BUDGETS_BY, required = false) String sortBy,
                                                     @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortOrder) {
        User user = authUtil.loggedInUser();
        BudgetResponse budgetResponse = budgetService.getAllBudgets(user, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(budgetResponse);
    }
}
