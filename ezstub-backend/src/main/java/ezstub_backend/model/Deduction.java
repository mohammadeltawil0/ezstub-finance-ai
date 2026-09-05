package ezstub_backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "paystub_deductions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deductionId;

    @NotBlank(message = "Deduction description is required!")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String description;

    @NotNull(message = "Current deduction amount is required!")
    @DecimalMin(value = "0.00")
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal currentAmount;

    @DecimalMin(value = "0.00")
    @Column(precision = 12, scale = 2)
    private BigDecimal ytdAmount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paystub_id", nullable = false)
    private Paystub paystub;
}
