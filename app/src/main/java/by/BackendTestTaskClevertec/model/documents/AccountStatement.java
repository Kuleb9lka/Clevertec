package by.BackendTestTaskClevertec.model.documents;

import by.BackendTestTaskClevertec.enums.AccountStatementPeriod;
import by.BackendTestTaskClevertec.model.Bank;
import by.BackendTestTaskClevertec.model.BankAccount;
import by.BackendTestTaskClevertec.model.Transaction;
import by.BackendTestTaskClevertec.model.User;
import by.BackendTestTaskClevertec.model.documents.Documents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatement implements Documents {

    private User user;

    private BankAccount bankAccount;

    private Bank bank;

    private Timestamp dateTime;

    private BigDecimal moneyLeft;

    private AccountStatementPeriod period;

    List<Transaction> listOfTransactions;


}
