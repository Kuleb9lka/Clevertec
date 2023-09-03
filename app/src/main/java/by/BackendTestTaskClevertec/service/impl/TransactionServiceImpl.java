package by.BackendTestTaskClevertec.service.impl;

import by.BackendTestTaskClevertec.enums.AccountStatementPeriod;
import by.BackendTestTaskClevertec.model.BankAccount;
import by.BackendTestTaskClevertec.model.Transaction;
import by.BackendTestTaskClevertec.repository.TransactionRepository;
import by.BackendTestTaskClevertec.service.TransactionService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    @Override
    public Transaction getById(long id) {

        return repository.getById(id);
    }

    @Override
    public Transaction create(Transaction transaction) {

        return repository.create(transaction);
    }

    @Override
    public void update(Transaction transaction) {

        repository.update(transaction);
    }

    @Override
    public void delete(long id) {

        repository.delete(id);
    }

    @Override
    public List<Transaction> getAllTransactionsByPeriod(BankAccount accountNumber, AccountStatementPeriod period) {

        return repository.getAllTransactionsForPeriod(accountNumber.getAccountNumber(), period);
    }
}
