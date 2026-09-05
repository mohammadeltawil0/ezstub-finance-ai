package ezstub_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "receipts",
        indexes = {
            @Index(name = "idx_receipt_user", columnList = "user_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptId;

    @Size(max = 500)
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Size(max = 200)
    @Column(length = 200)
    private String merchant;

    @DecimalMin(value = "0.00")
    @Column(precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Lob
    private String parsedJson;
    // raw output from Python OCR service

    @Builder.Default
    @Column(nullable = false)
    private Boolean processed = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
