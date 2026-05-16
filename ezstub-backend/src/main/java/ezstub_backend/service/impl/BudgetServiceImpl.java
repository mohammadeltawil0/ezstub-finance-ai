package ezstub_backend.service.impl;

import ezstub_backend.dto.BudgetDTO;
import ezstub_backend.mapper.BudgetMapper;
import ezstub_backend.model.Budget;
import ezstub_backend.model.User;
import ezstub_backend.repository.BudgetRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.service.BudgetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BudgetDTO createBudget(BudgetDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found!"));
        Budget budget = BudgetMapper.toEntity(dto, user);
        Budget saved = budgetRepository.save(budget);
        return BudgetMapper.toDTO(saved);
    }

    @Override
    public List<BudgetDTO> getBudgetsByUser(Long userId) {
        return budgetRepository.findByUserId(userId)
                .stream()
                .map(BudgetMapper::toDTO)
                .toList();
    }

    @Override
    public List<BudgetDTO> getBudgetsByUserAndMonth(Long userId, String month) {
        return budgetRepository.findByUserIdAndMonth(userId, month)
                .stream()
                .map(BudgetMapper::toDTO)
                .toList();
    }

    @Override
    public BudgetDTO getBudgetById(Long id) {

        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        return BudgetMapper.toDTO(budget);
    }

    @Override
    public BudgetDTO updateBudget(Long id, BudgetDTO dto) {

        Budget existing = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        existing.setCategory(dto.getCategory());
        existing.setLimitAmount(dto.getLimitAmount());
        existing.setMonth(dto.getMonth());

        Budget updated = budgetRepository.save(existing);

        return BudgetMapper.toDTO(updated);
    }

    @Override
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}
