package ezstub_backend.payload;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyIncomeDTO {

//    private Long companyIncomeId;
    private String companyName;
    private BigDecimal totalEarnedFromCompany;
    private BigDecimal totalDeductionsFromCompany;
    private BigDecimal hoursWorkedAtCompany;
    private LocalDate dateStarted;
    private LocalDate dateEnded;
    private Integer paystubCount;

}
