package ezstub_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollAnalysisDTO {

    private BigDecimal expectedGross;
    private BigDecimal actualGross;
    private BigDecimal difference;
    private boolean underpaid;
    private List<String> issues;
}
