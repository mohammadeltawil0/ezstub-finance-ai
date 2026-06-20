package ezstub_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptDTO {

    private Long id;
    private String imageUrl;
    private String merchant;
    private BigDecimal totalAmount;
    private String parsedJson;
    private Boolean processed;
    private LocalDateTime uploadedAt;
    private Long userId;
}
