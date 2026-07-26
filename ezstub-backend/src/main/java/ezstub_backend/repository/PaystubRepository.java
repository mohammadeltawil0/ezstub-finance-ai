package ezstub_backend.repository;

import ezstub_backend.model.Paystub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaystubRepository extends JpaRepository<Paystub, Long> {

    List<Paystub> findByUser_UserId(Long userId);
}