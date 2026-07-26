package ezstub_backend.repository;

import ezstub_backend.model.User;
import ezstub_backend.model.enums.AppRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByUserName(String username);
    Optional<User> findByUserName(String username);

    @Query("SELECT u " +
            "FROM User u " +
            "JOIN u.roles r " +
            "WHERE r.roleName = :role")
    Page<User> findByRoleName(@Param("role") AppRole role, Pageable pageable);

}
