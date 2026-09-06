package ezstub_backend.payload;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollAnalysisDTO {

    private Long payPeriodId;

    private Long paystubId;

    private String employer;

    private BigDecimal expectedGross;

    private BigDecimal actualGross;

    private BigDecimal difference;

    private BigDecimal expectedHours;

    private BigDecimal actualHours;

    private BigDecimal hourlyRate;

    private boolean underpaid;

    private boolean overpaid;

    @Builder.Default
    private List<String> issues = new ArrayList<>();
}
