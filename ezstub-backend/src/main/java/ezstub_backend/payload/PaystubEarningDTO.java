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
public class PaystubEarningDTO {

    @NotBlank(message = "Earning description is required")
    @Size(max = 150)
    private String description;

    @DecimalMin("0.00")
    private BigDecimal appliedRate;

    @DecimalMin("0.00")
    private BigDecimal hours;

    @NotNull(message = "Earnings amount is required")
    @DecimalMin("0.00")
    private BigDecimal earnings;
}