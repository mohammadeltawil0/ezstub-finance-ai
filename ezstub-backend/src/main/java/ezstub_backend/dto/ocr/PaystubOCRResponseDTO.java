package ezstub_backend.dto.ocr;

import ezstub_backend.dto.PaystubDeductionDTO;
import ezstub_backend.dto.PaystubEarningDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaystubOCRResponseDTO {

    private String employerName;

    private String employerLocation;

    private String payBeginDate;

    private String payEndDate;

    private String checkDate;

    private BigDecimal baseHourlyRate;

    private BigDecimal totalHoursWorked;

    private BigDecimal currentGross;

    private BigDecimal ytdGross;

    private BigDecimal currentNet;

    private BigDecimal ytdNet;

    private List<PaystubDeductionDTO> deductions;

    private List<PaystubEarningDTO> rateVariations;

    private String securityStatus;
}