package ezstub_backend.security;


import ezstub_backend.model.Role;
import ezstub_backend.model.User;
import ezstub_backend.model.enums.AppRole;
import ezstub_backend.repository.RoleRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.security.jwt.AuthEntryPointJwt;
import ezstub_backend.security.jwt.AuthTokenFilter;
import ezstub_backend.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Autowired
    DataSource dataSource;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(
                        session
                                -> session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
//                            .requestMatchers("/api/public/**").permitAll()
//                            .requestMatchers("/api/admin/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .anyRequest().authenticated());
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
        http.headers(
                headers -> headers.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::sameOrigin
                ));
        return http.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        ));
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder,
                                      PlatformTransactionManager platformTransactionManager) {

        return args -> {
            // 2. Create a template to control the transaction manually
            // This ensures the fetched Roles stay "attached" to the session when saving the Users
            TransactionTemplate txTemplate = new TransactionTemplate(platformTransactionManager);

            txTemplate.execute(status -> {
                // Retrieve/create roles
                Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                        .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));
                Role payrollAnalystRole = roleRepository.findByRoleName(AppRole.ROLE_PAYROLL_ANALYST)
                        .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_PAYROLL_ANALYST)));
                Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                        .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

                Set<Role> userRoles = Set.of(userRole);
                Set<Role> payrollAnalystRoles = Set.of(payrollAnalystRole);
                Set<Role> adminRoles = Set.of(adminRole);

                // Create users if not already present
                if (!userRepository.existsByUserName("user1")) {
                    User user1 = new User("user1", "Test", "User", "user1@example.com", passwordEncoder.encode("password1"));
                    user1.setRoles(userRoles);
                    userRepository.save(user1);
                }
                if (!userRepository.existsByUserName("seller1")) {
                    User seller1 = new User("payrollAnalyst1", "Payroll", "Analyst", "seller1@example.com", passwordEncoder.encode("password2"));
                    seller1.setRoles(payrollAnalystRoles);
                    userRepository.save(seller1);
                }
                if (!userRepository.existsByUserName("admin")) {
                    User admin = new User("admin", "Test", "Admin", "admin@example.com", passwordEncoder.encode("adminPass"));
                    admin.setRoles(adminRoles);
                    userRepository.save(admin);
                }
                return null;
            });
        };
    }



}
