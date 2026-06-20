package ezstub_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "paystub_earnings")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaystubEarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(precision = 12, scale = 2)
    private BigDecimal appliedRate;

    @Column(precision = 12, scale = 2)
    private BigDecimal hours;

    @Column(precision = 12, scale = 2)
    private BigDecimal earnings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paystub_id")
    private Paystub paystub;
}
