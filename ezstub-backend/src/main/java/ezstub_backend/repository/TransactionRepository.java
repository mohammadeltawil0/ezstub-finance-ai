package ezstub_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ezstub_backend.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
}
