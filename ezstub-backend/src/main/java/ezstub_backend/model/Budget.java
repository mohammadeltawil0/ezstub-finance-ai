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
@Table(name = "budgets",
        uniqueConstraints = {
        @UniqueConstraint(
        name = "uk_budget_user_category_month",
            columnNames = {
                    "user_id",
                    "category",
                    "month"
        })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;

    @NotNull(message = "Category is required!")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ExpenseCategory category; // Food, Travel, etc.

    @NotNull(message = "Limit amount is required!")
    @DecimalMin(value = "0.01", message = "Limit amount must be greater than 0!")
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal limitAmount;

    @NotBlank(message = "Month is required")
    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])$",
            message = "Month must be in YYYY-MM format!")
    @Column(nullable = false, length = 7)
    private String month; // "2026-05"

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
