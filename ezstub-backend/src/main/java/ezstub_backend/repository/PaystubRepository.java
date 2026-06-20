package ezstub_backend.repository;

import ezstub_backend.model.Paystub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaystubRepository extends JpaRepository<Paystub, Long> {

    List<Paystub> findByUserId(Long userId);
}