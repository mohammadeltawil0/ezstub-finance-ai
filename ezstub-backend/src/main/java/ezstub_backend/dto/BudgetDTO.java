package ezstub_backend.dto;

import ezstub_backend.model.enums.ExpenseCategory;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetDTO {

    private Long id;
    private ExpenseCategory category;
    private BigDecimal limitAmount;
    private String month;
    private Long userId;
}
