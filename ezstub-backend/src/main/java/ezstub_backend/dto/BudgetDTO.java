package ezstub_backend.dto;

import ezstub_backend.model.enums.ExpenseCategory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetDTO {

    private Long id;
    private ExpenseCategory category;
    private Double limitAmount;
    private String month;
    private Long userId;
}
