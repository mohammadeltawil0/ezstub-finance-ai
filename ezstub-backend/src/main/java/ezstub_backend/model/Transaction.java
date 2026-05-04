package ezstub_backend.model;

import ezstub_backend.model.enums.TransactionSource;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private String category; // Food, Rent, Transport, etc.

    private String description;

    private LocalDate date;

    private TransactionSource source;
    // "RECEIPT", "MANUAL", "PAYSTUB"

    private Double confidenceScore;
    // from ML model (0.0 - 1.0)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
