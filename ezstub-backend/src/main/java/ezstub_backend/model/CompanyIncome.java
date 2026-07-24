package ezstub_backend.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private BigDecimal totalEarnedFromCompany;
    private BigDecimal totalDeductionsFromCompany;
    private BigDecimal hoursWorkedAtCompany;
    private LocalDate dateStarted;
    private LocalDate dateEnded;
    private Integer paystubCount;
}
