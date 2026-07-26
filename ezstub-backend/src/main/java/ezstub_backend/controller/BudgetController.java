package ezstub_backend.controller;

import ezstub_backend.model.User;
import ezstub_backend.payload.BudgetDTO;
import ezstub_backend.service.BudgetService;
import ezstub_backend.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BudgetController {

    @Autowired
    AuthUtil authUtil;

    @Autowired
    BudgetService budgetService;

    @PostMapping("/budgets")
    public ResponseEntity<BudgetDTO> createBudget(@RequestBody BudgetDTO budgetDTO) {
        User user = authUtil.loggedInUser();
        BudgetDTO savedBudgetDTO = budgetService.createBudget(budgetDTO, user);
        return new ResponseEntity<>(savedBudgetDTO, HttpStatus.CREATED);
    }

//    @GetMapping("/user/{userId}")
//    public List<BudgetDTO> getBudgets(@PathVariable Long userId) {
//        return budgetService.getBudgetsByUser(userId);
//    }
//
//    @GetMapping("/user/{userId}/month/{month}")
//    public List<BudgetDTO> getBudgetsByMonth(@PathVariable Long userId, @PathVariable String month) {
//        return budgetService.getBudgetsByUserAndMonth(userId, month);
//    }
//
//    @GetMapping("/{id}")
//    public BudgetDTO getBudgetById(@PathVariable Long id) {
//        return budgetService.getBudgetById(id);
//    }
//
//    @PutMapping("/{id}")
//    public BudgetDTO updateBudget(
//            @PathVariable Long id,
//            @RequestBody BudgetDTO dto
//    ) {
//        return budgetService.updateBudget(id, dto);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteBudget(@PathVariable Long id) {
//        budgetService.deleteBudget(id);
//    }

}
