package ezstub_backend.payload;

import ezstub_backend.model.enums.ExpenseCategory;
import ezstub_backend.model.enums.IncomeCategory;
import ezstub_backend.model.enums.TransactionSource;
import ezstub_backend.model.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    private Long transactionId;

    @Size(max = 200)
    private String merchant;

    @NotNull(message = "Amount is required")
    @DecimalMin(
            value = "0.00",
            message = "Amount cannot be negative"
    )
    private BigDecimal amount;

    @NotNull(message = "Transaction date is required")
    private LocalDate transactionDate;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    private ExpenseCategory expenseCategory;

    private IncomeCategory incomeCategory;

    @NotNull(message = "Transaction source is required")
    private TransactionSource source;

    @Size(max = 500)
    private String description;

    private Long userId;

}
