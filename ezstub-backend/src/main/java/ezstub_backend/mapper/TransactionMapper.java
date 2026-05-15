package ezstub_backend.mapper;

import ezstub_backend.dto.TransactionDTO;
import ezstub_backend.model.Transaction;

import java.util.Locale;

public class TransactionMapper {

    public static Transaction toEntity(TransactionDTO dto) {
        Transaction t = new Transaction();
        t.setAmount(dto.getAmount());
        t.setCategory(dto.getCategory());
        t.setDescription(dto.getDescription());
        t.setDate(dto.getDate());
        t.setSource(dto.getSource());
        return t;
    }

    public static TransactionDTO toDTO(Transaction t) {
        return new TransactionDTO(
                t.getAmount(),
                t.getCategory(),
                t.getDescription(),
                t.getDate(),
                t.getSource()

        );
    }
}
