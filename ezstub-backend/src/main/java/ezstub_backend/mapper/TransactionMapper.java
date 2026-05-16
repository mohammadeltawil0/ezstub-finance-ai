package ezstub_backend.mapper;

import ezstub_backend.dto.TransactionDTO;
import ezstub_backend.model.Transaction;
import ezstub_backend.model.User;

import java.util.Locale;


public class TransactionMapper {

    public static Transaction toEntity(TransactionDTO dto, User user) {
        return Transaction.builder()
                .id(dto.getId())
                .merchant(dto.getMerchant())
                .amount(dto.getAmount())
                .transactionDate(dto.getTransactionDate())
                .type(dto.getType())
                .expenseCategory(dto.getExpenseCategory())
                .incomeCategory(dto.getIncomeCategory())
                .source(dto.getSource())
                .description(dto.getDescription())
                .user(user)
                .build();
    }

    public static TransactionDTO toDTO(Transaction t) {
        return TransactionDTO.builder()
                .id(t.getId())
                .merchant(t.getMerchant())
                .amount(t.getAmount())
                .transactionDate(t.getTransactionDate())
                .type(t.getType())
                .expenseCategory(t.getExpenseCategory())
                .incomeCategory(t.getIncomeCategory())
                .source(t.getSource())
                .description(t.getDescription())
                .userId(t.getUser().getId())
                .build();
    }
}
