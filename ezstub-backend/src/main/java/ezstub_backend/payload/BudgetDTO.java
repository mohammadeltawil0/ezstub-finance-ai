package ezstub_backend.payload;

import ezstub_backend.model.enums.ExpenseCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetDTO {

    private Long budgetId;

    @NotNull(message = "Category is required!")
    private ExpenseCategory category; // Food, Travel, etc.

    @NotNull(message = "Limit amount is required")
    @DecimalMin(value = "0.01", message = "Limit amount must be greater than 0!")
    private BigDecimal limitAmount;

    @NotBlank(message = "Month is required")
    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])$",
            message = "Month must be in YYYY-MM format!")
    private String month; // "2026-05"

    private Long userId;
}
