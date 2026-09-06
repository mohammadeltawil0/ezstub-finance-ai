package ezstub_backend.payload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptDTO {

    private Long receiptId;

    @Size(max = 500)
    private String imageUrl;

    @Size(max = 200)
    private String merchant;

    @DecimalMin("0.00")
    private BigDecimal totalAmount;

    private String parsedJson;

    private Boolean processed;

    private LocalDateTime createdAt;

    private Long userId;
}
