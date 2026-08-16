package ezstub_backend.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pay_periods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayPeriod extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payPeriodId;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate payDate;
    private String employer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "payPeriod")
    @Builder.Default
    private List<WorkDay> workDays = new ArrayList<>();

    @OneToOne(mappedBy = "payPeriod")
    private Paystub paystub;


}
