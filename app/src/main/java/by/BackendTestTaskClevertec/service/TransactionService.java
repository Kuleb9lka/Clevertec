package by.BackendTestTaskClevertec.service;

import by.BackendTestTaskClevertec.enums.AccountStatementPeriod;
import by.BackendTestTaskClevertec.model.BankAccount;
import by.BackendTestTaskClevertec.model.Transaction;

import java.util.List;

public interface TransactionService extends BankSystemService<Transaction> {

    List<Transaction> getAllTransactionsByPeriod(BankAccount accountNumber, AccountStatementPeriod period);
}
