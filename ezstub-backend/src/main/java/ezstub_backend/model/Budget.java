package ezstub_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category; // Food, Travel, etc.

    private Double limitAmount;

    private String month; // "2026-05"

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
