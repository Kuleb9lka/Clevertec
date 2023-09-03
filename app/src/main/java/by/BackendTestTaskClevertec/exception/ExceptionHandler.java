package by.BackendTestTaskClevertec.exception;

import by.BackendTestTaskClevertec.enums.AccountStatementPeriod;
import by.BackendTestTaskClevertec.model.Bank;
import by.BackendTestTaskClevertec.model.BankAccount;
import by.BackendTestTaskClevertec.model.Transaction;
import by.BackendTestTaskClevertec.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface ExceptionHandler {

    Transaction moneyTransfer(BankAccount accountSender, BankAccount accountReceiver, BigDecimal moneyToSend);
    String createAccountStatement(User user, String accountNumber, AccountStatementPeriod period);
    List<BankAccount> getAllBankAccountsByUser(User user);
    Bank getBankByBankAccount(BankAccount account);
    String writeCheck(Transaction transaction, Bank bankSender, Bank bankReceiver);
    BankAccount getBankAccountByNumber(String number);
}
