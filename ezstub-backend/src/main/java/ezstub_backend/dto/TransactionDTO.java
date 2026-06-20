package ezstub_backend.dto;

import ezstub_backend.model.enums.ExpenseCategory;
import ezstub_backend.model.enums.IncomeCategory;
import ezstub_backend.model.enums.TransactionSource;
import ezstub_backend.model.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    private Long id;
    private String merchant;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private TransactionType type;
    private ExpenseCategory expenseCategory;
    private IncomeCategory incomeCategory;
    private TransactionSource source;
    private String description;
    private Long userId;

}
