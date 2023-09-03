package by.BackendTestTaskClevertec.facade;

import by.BackendTestTaskClevertec.enums.AccountStatementPeriod;
import by.BackendTestTaskClevertec.model.Bank;
import by.BackendTestTaskClevertec.model.BankAccount;
import by.BackendTestTaskClevertec.model.Transaction;
import by.BackendTestTaskClevertec.model.User;
import by.BackendTestTaskClevertec.model.documents.AccountStatement;

import java.math.BigDecimal;
import java.util.List;

public interface FacadeService {

    Transaction moneyTransfer(BankAccount accountSender, BankAccount accountReceiver, BigDecimal moneyToSend) throws Exception;
    String createAccountStatement(User user, String accountNumber, AccountStatementPeriod period) throws Exception;
    List<BankAccount> getAllBankAccountsByUser(User user) throws Exception;
    Bank getBankByBankAccount(BankAccount account) throws Exception;
    String writeCheck(Transaction transaction, Bank bankSender, Bank bankReceiver);
    BankAccount getBankAccountByNumber(String number) throws Exception;





}
