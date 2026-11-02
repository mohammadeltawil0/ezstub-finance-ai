package ezstub_backend.payload.ocr;

import ezstub_backend.payload.modelDTOs.PaystubDeductionDTO;
import ezstub_backend.payload.modelDTOs.PaystubEarningDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaystubOCRResponseDTO {

    private String employerName;
    private String employerLocation;
    private LocalDate payBeginDate;
    private LocalDate payEndDate;
    private LocalDate checkDate;
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