package ezstub_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String userName;

    @NotBlank
    @Column(name = "email", unique = true, nullable = false)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password")
    private String password;

    //Relationships
    @OneToMany(mappedBy = "user")
    private List<WorkDay> workDays;

    @OneToMany(mappedBy = "user")
    private List<Paystub> paystubs;

    @OneToMany(mappedBy = "user")
    private List<Receipt> receipts;

    @OneToMany(mappedBy = "user")
    private List<Budget> budgets;
}
