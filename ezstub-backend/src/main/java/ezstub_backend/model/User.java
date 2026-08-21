package ezstub_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_users_email", columnNames = "email")
        })
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "First name is required!")
    @Size(max = 50, message = "First name cannot exceed 50 characters!")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required!")
    @Size(max = 50, message = "Last name cannot exceed 50 characters!")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Size(max = 100)
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(max = 120)
    @Column(name = "password", nullable = false, length = 120)
    private String password;

    //Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PayPeriod> payPeriods = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Paystub> paystubs = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<WorkDay> workDays = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Receipt> receipts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Budget> budgets = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

}
