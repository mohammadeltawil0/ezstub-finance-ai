package ezstub_backend.service.impl;

import ezstub_backend.dto.TransactionDTO;
import ezstub_backend.exception.InvalidTransactionException;
import ezstub_backend.mapper.TransactionMapper;
import ezstub_backend.model.Transaction;
import ezstub_backend.model.User;
import ezstub_backend.model.enums.TransactionType;
import ezstub_backend.repository.TransactionRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }


    @Override
    public TransactionDTO save(TransactionDTO dto) {

        // Check if transaction is valid
        if (dto.getType() == TransactionType.EXPENSE && dto.getExpenseCategory() == null) {
            throw new InvalidTransactionException("Expense must have expenseCategory");
        }
        if (dto.getType() == TransactionType.INCOME && dto.getIncomeCategory() == null) {
            throw new InvalidTransactionException("Income must have incomeCategory");
        }
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found!"));
        Transaction transaction = TransactionMapper.toEntity(dto, user);
        Transaction saved = transactionRepository.save(transaction);
        return TransactionMapper.toDTO(saved);
    }

    @Override
    public List<TransactionDTO> getByUserId(Long userId) {
        return transactionRepository.findByUserId(userId)
                .stream()
                .map(TransactionMapper::toDTO)
                .toList();
    }

    @Override
    public TransactionDTO getById(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        return TransactionMapper.toDTO(transaction);
    }

    @Override
    public TransactionDTO update(Long id, TransactionDTO dto) {

        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        existing.setMerchant(dto.getMerchant());
        existing.setAmount(dto.getAmount());
        existing.setTransactionDate(dto.getTransactionDate());
        existing.setType(dto.getType());
        existing.setExpenseCategory(dto.getExpenseCategory());
        existing.setIncomeCategory(dto.getIncomeCategory());
        existing.setSource(dto.getSource());
        existing.setDescription(dto.getDescription());

        Transaction updated = transactionRepository.save(existing);

        return TransactionMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}
