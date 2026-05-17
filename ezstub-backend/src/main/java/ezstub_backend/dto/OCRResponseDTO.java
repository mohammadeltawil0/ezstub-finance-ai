package ezstub_backend.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OCRResponseDTO {

    private String merchant;
    private Double amount;
    private String rawText;
}
