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
        Transaction t = TransactionMapper.toEntity(dto);
        Transaction saved = transactionService.save(t);

        return TransactionMapper.toDTO(saved);

    }

    @GetMapping("/user/{userId}")
    public List<TransactionDTO> getTransactions(@PathVariable Long userId) {
        return transactionService.getByUserId(userId)
                .stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }
}
