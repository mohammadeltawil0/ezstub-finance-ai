package ezstub_backend.repository;

import ezstub_backend.model.Budget;
import ezstub_backend.model.enums.ExpenseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Page<Budget> findByUser_UserId(Long userId, Pageable pageable);

    boolean existsByUser_UserIdAndCategoryAndMonth(Long userId, ExpenseCategory category, String month);

    Page<Budget> findByUser_UserIdAndMonth(Long userId, String month, Pageable pageable);
}
