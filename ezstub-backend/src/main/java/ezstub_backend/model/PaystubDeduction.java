package ezstub_backend.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "paystub_deductions")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaystubDeduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(precision = 12, scale = 2)
    private BigDecimal currentAmount;

    @Column(precision = 12, scale = 2)
    private BigDecimal ytdAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paystub_id")
    private Paystub paystub;
}
