package ezstub_backend.model;

import ezstub_backend.model.enums.ExpenseCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    private ExpenseCategory category; // Food, Travel, etc.

    @NotNull(message = "Limit amount is required")
    @DecimalMin(value = "0.01", message = "Limit amount must be greater than 0")
    private BigDecimal limitAmount;

    @NotBlank(message = "Month is required")
    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])$",
            message = "Month must be in YYYY-MM format")
    private String month; // "2026-05"

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
