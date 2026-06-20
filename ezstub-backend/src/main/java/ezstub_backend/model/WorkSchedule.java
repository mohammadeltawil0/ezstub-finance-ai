package ezstub_backend.model;

import ezstub_backend.model.enums.ScheduleType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "work_schedules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate workDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer unpaidBreakMinutes;

    private BigDecimal hourlyRate;

    private BigDecimal expectedGrossPay;

    @Enumerated(EnumType.STRING)
    private ScheduleType type;

    private String notes;

    private String employer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
