package ezstub_backend.repository;

import ezstub_backend.model.PayPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayPeriodRepository extends JpaRepository<PayPeriod, Long> {

    List<PayPeriod> findByUser_UserId(Long userId);

    List<PayPeriod> findByUser_UserIdAndEmployer(Long userId, String employer);

}
