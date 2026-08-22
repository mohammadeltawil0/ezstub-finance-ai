package ezstub_backend.model;

import ezstub_backend.model.enums.ScheduleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "work_days",
        indexes = {
            @Index(name = "idx_work_day_user", columnList = "user_id"),
                @Index(name = "idx_work_day_pay_period", columnList = "pay_period_id"),
                @Index(name = "idx_work_day_date", columnList = "work_date")
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkDay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workDayId;

    @NotNull(message = "Work date is required!")
    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "punch_in")
    private LocalTime punchIn;

    @Column(name = "punch_out")
    private LocalTime punchOut;

    @Min(value = 0, message = "Break cannot be negative!")
    @Column(name = "unpaid_break_minutes")
    private Integer unpaidBreakMinutes;

    @DecimalMin(value = "0.00", inclusive = true)
    @Column(precision = 12, scale = 2, name = "hourly_rate")
    private BigDecimal hourlyRate;

    @DecimalMin(value = "0.00", inclusive = true)
    @Column(precision = 12, scale = 2, name = "expected_gross_pay")
    private BigDecimal expectedGrossPay;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type", length = 30)
    private ScheduleType scheduleType;

    @Size(max = 150)
    @Column(length = 150)
    private String employer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_work_day_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_period_id", foreignKey = @ForeignKey(name = "fk_work_day_pay_period"))
    private PayPeriod payPeriod;
}
