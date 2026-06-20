package ezstub_backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaystubEarningDTO {

    private String description;

    private BigDecimal appliedRate;

    private BigDecimal hours;

    private BigDecimal earnings;
}