package ezstub_backend.model;

import ezstub_backend.model.enums.ExpenseCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category; // Food, Travel, etc.

    private BigDecimal limitAmount;

    private String month; // "2026-05"

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
