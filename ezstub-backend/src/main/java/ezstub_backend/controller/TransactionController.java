package ezstub_backend.controller;

import ezstub_backend.dto.TransactionDTO;
import ezstub_backend.mapper.TransactionMapper;
import ezstub_backend.model.Transaction;
import ezstub_backend.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionSerive) {
        this.transactionService = transactionSerive;
    }

    @PostMapping
    public TransactionDTO createTransaction(@RequestBody TransactionDTO dto) {
        return transactionService.save(dto);
    }

    @GetMapping("/{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id) {
        return transactionService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<TransactionDTO> getTransactions(@PathVariable Long userId) {
        return transactionService.getByUserId(userId);
    }

    @PutMapping("/{id}")
    public TransactionDTO updateTransaction(
            @PathVariable Long id,
            @RequestBody TransactionDTO dto
    ) {
        return transactionService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
    }
}
