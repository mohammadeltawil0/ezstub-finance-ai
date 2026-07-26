package ezstub_backend.service.impl;

import ezstub_backend.payload.BudgetDTO;
import ezstub_backend.model.Budget;
import ezstub_backend.model.User;
import ezstub_backend.repository.BudgetRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.service.BudgetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    BudgetRepository budgetRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public BudgetDTO createBudget(BudgetDTO budgetDTO, User user) {
        Budget budget = modelMapper.map(budgetDTO, Budget.class);
        List<Budget> budgetList = user.getBudgets();
        budgetList.add(budget);
        user.setBudgets(budgetList);
        budget.setUser(user);
        Budget savedBudget = budgetRepository.save(budget);
        return modelMapper.map(savedBudget, BudgetDTO.class);

    }

//    @Override
//    public List<BudgetDTO> getBudgetsByUser(Long userId) {
//        return budgetRepository.findByUserId(userId)
//                .stream()
//                .map(BudgetMapper::toDTO)
//                .toList();
//    }
//
//    @Override
//    public List<BudgetDTO> getBudgetsByUserAndMonth(Long userId, String month) {
//        return budgetRepository.findByUserIdAndMonth(userId, month)
//                .stream()
//                .map(BudgetMapper::toDTO)
//                .toList();
//    }
//
//    @Override
//    public BudgetDTO getBudgetById(Long id) {
//
//        Budget budget = budgetRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Budget not found"));
//
//        return BudgetMapper.toDTO(budget);
//    }
//
//    @Override
//    public BudgetDTO updateBudget(Long id, BudgetDTO dto) {
//
//        Budget existing = budgetRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Budget not found"));
//
//        existing.setCategory(dto.getCategory());
//        existing.setLimitAmount(dto.getLimitAmount());
//        existing.setMonth(dto.getMonth());
//
//        Budget updated = budgetRepository.save(existing);
//
//        return BudgetMapper.toDTO(updated);
//    }
//
//    @Override
//    public void deleteBudget(Long id) {
//        budgetRepository.deleteById(id);
//    }
}
