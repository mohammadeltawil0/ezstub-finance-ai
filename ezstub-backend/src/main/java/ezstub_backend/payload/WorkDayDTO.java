package ezstub_backend.payload;

import ezstub_backend.model.enums.ScheduleType;
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
    private LocalDate workDate;
    private LocalTime punchIn;
    private LocalTime punchOut;
    private Integer unpaidBreakMinutes;
    private BigDecimal hourlyRate;
    private BigDecimal expectedGrossPay;
    private ScheduleType scheduleType;
    private String employer;

    private Long documentId;
    private Long userId;
    private Long payPeriodId;
}
