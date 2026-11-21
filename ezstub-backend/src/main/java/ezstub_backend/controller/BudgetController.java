package ezstub_backend.controller;

import ezstub_backend.config.AppConstants;
import ezstub_backend.model.User;
import ezstub_backend.payload.apiResponses.PageResponse;
import ezstub_backend.payload.modelDTOs.BudgetDTO;
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
    public ResponseEntity<PageResponse<BudgetDTO>> getBudgets(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BUDGETS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortOrder) {
        User user = authUtil.loggedInUser();
        PageResponse<BudgetDTO> budgetResponse = budgetService.getAllBudgets(user, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(budgetResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDTO> getBudgetById(@PathVariable Long id) {
        BudgetDTO response = budgetService.getBudgetById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/month/{month}")
    public ResponseEntity<List<BudgetDTO>> getBudgetByMonth(@PathVariable String month) {
        Long userId = authUtil.loggedInUserId();
        List<BudgetDTO> response = budgetService.getBudgetsByUserAndMonth(userId, month);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetDTO> updateBudget(@PathVariable Long id, @Valid @RequestBody BudgetDTO dto) {
        return ResponseEntity.ok(budgetService.updateBudget(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
