package ezstub_backend.service.impl;

import ezstub_backend.exception.APIException;
import ezstub_backend.exception.ResourceNotFoundException;
import ezstub_backend.payload.apiResponses.PageResponse;
import ezstub_backend.payload.modelDTOs.BudgetDTO;
import ezstub_backend.model.Budget;
import ezstub_backend.model.User;
import ezstub_backend.repository.BudgetRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.service.BudgetService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Transactional
    public BudgetDTO createBudget(BudgetDTO budgetDTO, User user) {

        // check if the user already has a budget for this category and month
        boolean budgetExists = budgetRepository
                .existsByUser_UserIdAndCategoryAndMonth(
                        user.getUserId(),
                        budgetDTO.getCategory(),
                        budgetDTO.getMonth()
                );

        if (budgetExists) {
            throw new APIException("A budget already exists for this category and month!");
        }

        Budget budget = modelMapper.map(budgetDTO, Budget.class);
        budget.setUser(user);
        Budget savedBudget = budgetRepository.save(budget);
        BudgetDTO savedBudgetDTO = modelMapper.map(savedBudget, BudgetDTO.class);
        if (budget.getUser() != null) {
            savedBudgetDTO.setUserId(user.getUserId());
        }
        return savedBudgetDTO;
    }

    @Override
    @Transactional
    public PageResponse<BudgetDTO> getAllBudgets(User user, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Budget> budgetPage = budgetRepository.findByUser_UserId(user.getUserId(), pageable);

        List<BudgetDTO> budgetDTOS = budgetPage.getContent()
                .stream()
                .map(budget -> {
                    BudgetDTO savedBudgetDTO = modelMapper.map(budget, BudgetDTO.class);
                    if (budget.getUser() != null) {
                        savedBudgetDTO.setUserId(user.getUserId());
                    }
                    return savedBudgetDTO;
                }).toList();
        return PageResponse.<BudgetDTO>builder()
                .content(budgetDTOS)
                .pageNumber(budgetPage.getNumber())
                .pageSize(budgetPage.getSize())
                .totalElements(budgetPage.getTotalElements())
                .totalPages(budgetPage.getTotalPages())
                .lastPage(budgetPage.isLast())
                .build();
    }

    @Override
    public List<BudgetDTO> getBudgetsByUser(Long userId) {
        return budgetRepository.findByUser_UserId(userId, Pageable.unpaged())
                .getContent()
                .stream()
                .map(budget -> {
                    BudgetDTO savedBudgetDTO = modelMapper.map(budget, BudgetDTO.class);
                    if (budget.getUser() != null) {
                        savedBudgetDTO.setUserId(budget.getUser().getUserId());
                    }
                    return savedBudgetDTO;
                }).toList();
    }

    @Override
    public List<BudgetDTO> getBudgetsByUserAndMonth(Long userId, String month) {
        return budgetRepository.findByUser_UserIdAndMonth(userId, month, Pageable.unpaged())
                .getContent()
                .stream()
                .map(budget -> {
                    BudgetDTO savedBudgetDTO = modelMapper.map(budget, BudgetDTO.class);
                    if (budget.getUser() != null) {
                        savedBudgetDTO.setUserId(budget.getUser().getUserId());
                    }
                    return savedBudgetDTO;
                }).toList();
    }

    @Override
    public BudgetDTO getBudgetById(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget", "budgetId", id));
        BudgetDTO savedBudgetDTO = modelMapper.map(budget, BudgetDTO.class);
        if (budget.getUser() != null) {
            savedBudgetDTO.setUserId(budget.getUser().getUserId());
        }
        return savedBudgetDTO;
    }

    @Override
    public BudgetDTO updateBudget(Long id, BudgetDTO dto) {
        Budget existing = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget", "budgetId", id));

        existing.setCategory(dto.getCategory());
        existing.setLimitAmount(dto.getLimitAmount());
        existing.setMonth(dto.getMonth());

        BudgetDTO savedBudgetDTO = modelMapper.map(existing, BudgetDTO.class);
        if (existing.getUser() != null) {
            savedBudgetDTO.setUserId(existing.getUser().getUserId());
        }
        return savedBudgetDTO;
    }

    @Override
    public void deleteBudget(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget", "budgetId", id));
        budgetRepository.delete(budget);
    }


}
