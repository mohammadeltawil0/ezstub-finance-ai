package ezstub_backend.model;

import ezstub_backend.model.enums.IncomeCategory;
import ezstub_backend.model.enums.ExpenseCategory;
import ezstub_backend.model.enums.TransactionSource;
import ezstub_backend.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchant;

    private Double amount;

    private LocalDate transactionDate;

    // tells if it's income or expense
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    // Expense category (ONLY used if type = EXPENSE)
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    // Income category (ONLY used if type = INCOME)
    @Enumerated(EnumType.STRING)
    private IncomeCategory incomeCategory;

    // How transaction was created
    @Enumerated(EnumType.STRING)
    private TransactionSource source;

    private String description;
    // Relationship
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}


