package ezstub_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "income")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double grossIncome;

    private Double taxes;

    private Double netIncome;

    private LocalDate payPeriodStart;

    private LocalDate payPeriodEnd;

    private String employer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
