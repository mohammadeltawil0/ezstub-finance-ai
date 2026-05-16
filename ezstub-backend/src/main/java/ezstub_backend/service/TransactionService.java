package ezstub_backend.service;

import ezstub_backend.dto.TransactionDTO;
import ezstub_backend.model.Transaction;
import ezstub_backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    TransactionDTO save(TransactionDTO dto);

    List<TransactionDTO> getByUserId(Long userId);

    TransactionDTO getById(Long id);

    TransactionDTO update(Long id, TransactionDTO dto);

    void delete(Long id);
}
