package by.BackendTestTaskClevertec.exception;

import by.BackendTestTaskClevertec.enums.AccountStatementPeriod;
import by.BackendTestTaskClevertec.facade.FacadeService;
import by.BackendTestTaskClevertec.model.Bank;
import by.BackendTestTaskClevertec.model.BankAccount;
import by.BackendTestTaskClevertec.model.Transaction;
import by.BackendTestTaskClevertec.model.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ExceptionHandlerImpl implements ExceptionHandler {

    private final FacadeService facadeService;

    @Override
    public Transaction moneyTransfer(BankAccount accountSender, BankAccount accountReceiver, BigDecimal moneyToSend){

        Transaction transaction = new Transaction();

        try{

            transaction = facadeService.moneyTransfer(accountSender, accountReceiver, moneyToSend);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return transaction;
    }

    @Override
    public String createAccountStatement(User user, String accountNumber, AccountStatementPeriod period){

        String accountStatement = "";

        try{

            accountStatement = facadeService.createAccountStatement(user, accountNumber, period);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accountStatement;
    }

    @Override
    public List<BankAccount> getAllBankAccountsByUser(User user){

        List<BankAccount> bankAccounts = new ArrayList<>();

        try{

            bankAccounts = facadeService.getAllBankAccountsByUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bankAccounts;
    }

    @Override
    public Bank getBankByBankAccount(BankAccount account) {

        Bank bank = new Bank();

        try{

            bank = facadeService.getBankByBankAccount(account);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bank;
    }

    @Override
    public String writeCheck(Transaction transaction, Bank bankSender, Bank bankReceiver) {

        String check = "";

        try{

            check = facadeService.writeCheck(transaction, bankSender, bankReceiver);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return check;
    }

    @Override
    public BankAccount getBankAccountByNumber(String number){

        BankAccount bankAccount = null;

        try{

            bankAccount = facadeService.getBankAccountByNumber(number);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bankAccount;
    }
}
