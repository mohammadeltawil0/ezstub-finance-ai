package ezstub_backend.repository;

import ezstub_backend.model.PayPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PayPeriodRepository extends JpaRepository<PayPeriod, Long> {

    List<PayPeriod> findByUser_UserId(Long userId);

    List<PayPeriod> findByUser_UserIdAndEmployer(Long userId, String employer);

    Optional<PayPeriod> findByUser_UserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long userId, LocalDate date1, LocalDate date2
    );
}
