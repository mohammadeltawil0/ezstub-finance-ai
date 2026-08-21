package ezstub_backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pay_periods",
        indexes = {
            @Index(name = "idx_pay_period_user", columnList = "user_id"),
            @Index(name = "idx_pay_period_dates", columnList = "start_date, end_date")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayPeriod extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payPeriodId;

    @NotNull(message = "Start date is required!")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date is required!")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull(message = "Pay date is required!")
    @Column(name = "pay_date", nullable = false)
    private LocalDate payDate;

    @NotBlank(message = "Employer is required!")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String employer;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pay_period_user"))
    private User user;


    @OneToMany(mappedBy = "payPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WorkDay> workDays = new ArrayList<>();

    @OneToOne(mappedBy = "payPeriod", fetch = FetchType.LAZY)
    private Paystub paystub;


}
