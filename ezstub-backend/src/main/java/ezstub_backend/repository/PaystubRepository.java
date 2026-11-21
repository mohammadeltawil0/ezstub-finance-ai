package ezstub_backend.repository;

import ezstub_backend.model.Paystub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaystubRepository extends JpaRepository<Paystub, Long> {

    Page<Paystub> findByUser_UserId(Long userId, Pageable pageable);
    List<Paystub> findByUser_UserId(Long userId);
    Page<Paystub> findByEmployerName(String employerName, Pageable pageable);
}