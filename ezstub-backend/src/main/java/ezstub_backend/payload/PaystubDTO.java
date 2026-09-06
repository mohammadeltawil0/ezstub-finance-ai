package ezstub_backend.payload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaystubDTO {

    private Long paystubId;

    @NotBlank(message = "Employer name is required")
    @Size(max = 150)
    private String employerName;

    @Size(max = 250)
    private String employerLocation;

    @NotNull(message = "Pay begin date is required")
    private LocalDate payBeginDate;

    @NotNull(message = "Pay end date is required")
    private LocalDate payEndDate;

    @NotNull(message = "Check date is required")
    private LocalDate checkDate;

    @DecimalMin("0.00")
    private BigDecimal baseHourlyRate;

    @DecimalMin("0.00")
    private BigDecimal totalHoursWorked;

    @DecimalMin("0.00")
    private BigDecimal currentGross;

    @DecimalMin("0.00")
    private BigDecimal ytdGross;

    @DecimalMin("0.00")
    private BigDecimal currentNet;

    @DecimalMin("0.00")
    private BigDecimal ytdNet;

    private Boolean verified;

    private LocalDate uploadedDate;

    @Valid
    @Builder.Default
    private List<PaystubDeductionDTO> deductions =
            new ArrayList<>();

    @Valid
    @Builder.Default
    private List<PaystubEarningDTO> earnings =
            new ArrayList<>();

    private Long userId;

    private Long payPeriodId;
}
