package ezstub_backend.service;

import ezstub_backend.model.Transaction;
import ezstub_backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction save(Transaction transaction) {

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getByUserId(Long userId) {

        return transactionRepository.findByUserId(userId);
    }
}
