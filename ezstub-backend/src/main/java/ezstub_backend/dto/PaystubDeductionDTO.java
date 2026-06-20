package ezstub_backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaystubDeductionDTO {

    private String description;

    private BigDecimal currentAmount;

    private BigDecimal ytdAmount;
}