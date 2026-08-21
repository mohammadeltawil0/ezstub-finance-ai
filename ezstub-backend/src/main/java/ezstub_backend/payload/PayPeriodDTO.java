package ezstub_backend.payload;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayPeriodDTO {

    private Long payPeriodId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate payDate;
    private String employer;
    private Long userId;
    private Long paystubId;
}
