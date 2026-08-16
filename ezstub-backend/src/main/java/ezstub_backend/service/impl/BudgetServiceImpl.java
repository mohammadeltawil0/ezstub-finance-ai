package ezstub_backend.service.impl;

import ezstub_backend.exception.APIException;
import ezstub_backend.payload.BudgetDTO;
import ezstub_backend.model.Budget;
import ezstub_backend.model.User;
import ezstub_backend.payload.BudgetResponse;
import ezstub_backend.repository.BudgetRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.service.BudgetService;
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
    public BudgetDTO createBudget(BudgetDTO budgetDTO, User user) {

        // check if the user already has a budget for this category and month
        boolean budgetExists = budgetRepository
                .existsByUser_UserIdAndCategoryAndMonth(
                        user.getUserId(),
                        budgetDTO.getCategory(),
                        budgetDTO.getMonth()
                );

        if (budgetExists) {
            throw new APIException("A budget already exists for this category and month");
        }

        Budget budget = modelMapper.map(budgetDTO, Budget.class);
        budget.setUser(user);
        Budget savedBudget = budgetRepository.save(budget);
        BudgetDTO savedBudgetDTO = modelMapper.map(savedBudget, BudgetDTO.class);
        savedBudgetDTO.setUserId(user.getUserId());
        return savedBudgetDTO;
    }

    @Override
    public BudgetResponse getAllBudgets(User user, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Budget> budgetPage = budgetRepository.findByUser_UserId(user.getUserId(), pageable);

        var budgetDTOs = budgetPage.getContent()
                .stream()
                .map(budget -> {
                    BudgetDTO dto = modelMapper.map(budget, BudgetDTO.class);
                    dto.setUserId(user.getUserId());
                    return dto;
                })
                .toList();

        return new BudgetResponse(
                budgetDTOs,
                budgetPage.getNumber(),
                budgetPage.getSize(),
                budgetPage.getTotalElements(),
                budgetPage.getTotalPages(),
                budgetPage.isLast()
        );
    }


}
