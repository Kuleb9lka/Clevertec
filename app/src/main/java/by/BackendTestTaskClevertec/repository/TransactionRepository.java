package by.BackendTestTaskClevertec.repository;

import by.BackendTestTaskClevertec.enums.AccountStatementPeriod;
import by.BackendTestTaskClevertec.model.Transaction;

import java.util.List;

public interface TransactionRepository extends BankSystemRepository<Transaction> {

    List<Transaction> getAllTransactionsForPeriod(String accountNumber, AccountStatementPeriod period);
}
