package ezstub_backend.model;

import ezstub_backend.model.enums.ExpenseCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category; // Food, Travel, etc.

    private Double limitAmount;

    private String month; // "2026-05"

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
