package ezstub_backend.repository;

import ezstub_backend.model.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Page<Receipt> findByUser_UserId(Long userId, Pageable pageable);
    List<Receipt> findByUser_UserId(Long userId);
}
