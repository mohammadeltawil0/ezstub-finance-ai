package ezstub_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paystubs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paystub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paystubId;


    @NotBlank(message = "Employer name is required!")
    @Size(max = 150)
    @Column(name = "employer_name", nullable = false, length = 150)
    private String employerName;

    @Size(max = 250)
    @Column(name = "employer_location", length = 250)
    private String employerLocation;

    @NotNull(message = "Pay begin date is required!")
    @Column(name = "pay_begin_date", nullable = false)
    private LocalDate payBeginDate;

    @NotNull(message = "Pay end date is required!")
    @Column(name = "pay_end_date", nullable = false)
    private LocalDate payEndDate;

    @NotNull(message = "Check date is required!")
    @Column(name = "check_date", nullable = false)
    private LocalDate checkDate;

    @DecimalMin(value = "0.00")
    @Column(precision = 12, scale = 2, name = "base_hourly_rate")
    private BigDecimal baseHourlyRate;

    @DecimalMin(value = "0.00")
    @Column(precision = 12, scale = 2, name = "total_hours_worked")
    private BigDecimal totalHoursWorked;

    @DecimalMin(value = "0.00")
    @Column(precision = 12, scale = 2, name = "total_hours_worked")
    private BigDecimal currentGross;

    @DecimalMin(value = "0.00")
    @Column(precision = 12, scale = 2, name = "ytd_gross")
    private BigDecimal ytdGross;

    @DecimalMin(value = "0.00")
    @Column(precision = 12, scale = 2, name = "current_net")
    private BigDecimal currentNet;

    @DecimalMin(value = "0.00")
    @Column(precision = 12, scale = 2, name = "ytd_net")
    private BigDecimal ytdNet;

    @Builder.Default
    @Column(nullable = false)
    private Boolean verified = false;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    @OneToMany(mappedBy = "paystub", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Deduction> deductions = new ArrayList<>();

    @OneToMany(mappedBy = "paystub", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Earning> earnings = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;





}
