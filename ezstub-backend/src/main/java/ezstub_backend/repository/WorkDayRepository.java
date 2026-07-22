package ezstub_backend.repository;

import ezstub_backend.model.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {

    List<WorkDay> findByUserId(Long userId);
}
