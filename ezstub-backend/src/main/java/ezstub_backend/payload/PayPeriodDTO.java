package ezstub_backend.payload;

import ezstub_backend.model.WorkDay;
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
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate payDate;
    private String employer;
    private Long userId;
    private List<WorkDay> workDays = new ArrayList<>();
    private Long paystubId;
}
