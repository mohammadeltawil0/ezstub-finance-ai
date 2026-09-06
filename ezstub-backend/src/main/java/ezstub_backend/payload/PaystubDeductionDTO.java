package ezstub_backend.payload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaystubDeductionDTO {

    private Long deductionId;

    @NotBlank(message = "Deduction description is required")
    @Size(max = 150)
    private String description;

    @NotNull(message = "Current amount is required")
    @DecimalMin("0.00")
    private BigDecimal currentAmount;

    @DecimalMin("0.00")
    private BigDecimal ytdAmount;
}