package ezstub_backend.dto;

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
public class WorkScheduleDTO {

    private Long id;
    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer unpaidBreakMinutes;
    private BigDecimal hourlyRate;
    private BigDecimal expectedGrossPay;
    private ScheduleType type;
    private String notes;
    private String employer;
    private Long userId;
}
