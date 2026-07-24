package ezstub_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<WorkDay> workSchedules;

    @OneToMany(mappedBy = "user")
    private List<Paystub> paystubs;

    @OneToMany(mappedBy = "user")
    private List<Receipt> receipts;

    @OneToMany(mappedBy = "user")
    private List<Budget> budgets;
}
