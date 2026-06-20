package ezstub_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "paystubs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paystub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employerName;

    private String employerLocation;

    private LocalDate payBeginDate;

    private LocalDate payEndDate;

    private LocalDate checkDate;

    @Column(precision = 12, scale = 2)
    private BigDecimal baseHourlyRate;

    @Column(precision = 12, scale = 2)
    private BigDecimal totalHoursWorked;

    @Column(precision = 12, scale = 2)
    private BigDecimal currentGross;

    @Column(precision = 12, scale = 2)
    private BigDecimal ytdGross;

    @Column(precision = 12, scale = 2)
    private BigDecimal currentNet;

    @Column(precision = 12, scale = 2)
    private BigDecimal ytdNet;

    private Boolean verified;

//    @Lob
//    @Column(columnDefinition = "TEXT")
//    private String rawOCRText;

    private LocalDateTime uploadedAt;

    @OneToMany(
            mappedBy = "paystub",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<PaystubDeduction> deductions = new ArrayList<>();

    @OneToMany(
            mappedBy = "paystub",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<PaystubEarning> earnings = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;





}
