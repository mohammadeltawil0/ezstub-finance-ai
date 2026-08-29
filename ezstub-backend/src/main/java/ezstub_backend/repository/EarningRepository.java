package ezstub_backend.repository;

import ezstub_backend.model.Earning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EarningRepository extends JpaRepository<Earning, Long> {

    List<Earning> findByPaystub_PaystubId(Long paystubId);
}
