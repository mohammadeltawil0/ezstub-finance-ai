package ezstub_backend.dto;

import ezstub_backend.model.enums.TransactionSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private Double amount;
    private String category;
    private String description;
    private LocalDate date;
    private TransactionSource source;
}
