package ezstub_backend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyIncomeDTO {

    private Long companyIncomeId;

    private String companyName;
    private BigDecimal totalEarnedFromCompany;
    private BigDecimal totalDeductionsFromCompany;
    private BigDecimal hoursWorkedAtCompany;
    private LocalDate dateStarted;
    private LocalDate dateEnded;
    private Integer paystubCount;

}
