package ezstub_backend.mapper;

import ezstub_backend.dto.BudgetDTO;
import ezstub_backend.model.Budget;
import ezstub_backend.model.User;

public class BudgetMapper {

    public static Budget toEntity(BudgetDTO dto, User user) {
        return Budget.builder()
                .id(dto.getId())
                .category(dto.getCategory())
                .limitAmount(dto.getLimitAmount())
                .month(dto.getMonth())
                .user(user)
                .build();
    }

    public static BudgetDTO toDTO(Budget budget) {
        return BudgetDTO.builder()
                .id(budget.getId())
                .category(budget.getCategory())
                .limitAmount(budget.getLimitAmount())
                .month(budget.getMonth())
                .userId(budget.getUser().getId())
                .build();
    }
}
