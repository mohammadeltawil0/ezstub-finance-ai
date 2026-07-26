package ezstub_backend.repository;

import ezstub_backend.model.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {

    List<WorkDay> findByUser_UserId(Long userId);
}
