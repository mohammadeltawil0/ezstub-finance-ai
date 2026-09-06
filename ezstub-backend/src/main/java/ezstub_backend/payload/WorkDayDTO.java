package ezstub_backend.payload;

import ezstub_backend.model.enums.ScheduleType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkDayDTO {

    private Long workDayId;

    @NotNull(message = "Work date is required")
    private LocalDate workDate;

    private LocalTime punchIn;
    private LocalTime punchOut;

    @Min(value = 0, message = "Break minutes cannot be negative")
    private Integer unpaidBreakMinutes;

    @DecimalMin("0.00")
    private BigDecimal hourlyRate;

    @DecimalMin("0.00")
    private BigDecimal expectedGrossPay;

    private ScheduleType scheduleType;

    @Size(max = 150)
    private String employer;

    private Long documentId;
    private Long userId;
    private Long payPeriodId;
}
