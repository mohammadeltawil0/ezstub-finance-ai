package ezstub_backend.exception;

import ezstub_backend.model.Transaction;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
