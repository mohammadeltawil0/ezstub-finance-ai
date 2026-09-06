package ezstub_backend.payload;

import ezstub_backend.model.WorkDay;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayPeriodDTO {

    private Long payPeriodId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Pay date is required")
    private LocalDate payDate;

    @NotBlank(message = "Employer is required")
    @Size(max = 150)
    private String employer;

    private Long userId;

    @Builder.Default
    private List<WorkDayDTO> workDays = new ArrayList<>();

    private Long paystubId;

    @AssertTrue(message = "End date must be on or after start date!")
    public boolean isValidDateRange() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return !endDate.isBefore(startDate);
    }
}
