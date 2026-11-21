package ezstub_backend.repository;

import ezstub_backend.model.WorkDay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
    Page<WorkDay> findByUser_UserId(Long userId, Pageable pageable);
    List<WorkDay> findByUser_UserId(Long userId);
}
