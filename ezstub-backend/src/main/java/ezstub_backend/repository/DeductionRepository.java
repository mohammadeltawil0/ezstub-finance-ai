package ezstub_backend.repository;

import ezstub_backend.model.Deduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeductionRepository extends JpaRepository<Deduction, Long> {

    List<Deduction> findByPaystub_PaystubId(Long paystubId);
}
