package by.BackendTestTaskClevertec.facade;

import by.BackendTestTaskClevertec.constant.Constant;
import by.BackendTestTaskClevertec.enums.AccountStatementPeriod;
import by.BackendTestTaskClevertec.model.Bank;
import by.BackendTestTaskClevertec.model.BankAccount;
import by.BackendTestTaskClevertec.model.Transaction;
import by.BackendTestTaskClevertec.model.User;
import by.BackendTestTaskClevertec.model.documents.AccountStatement;
import by.BackendTestTaskClevertec.service.BankAccountService;
import by.BackendTestTaskClevertec.service.BankService;
import by.BackendTestTaskClevertec.service.TransactionService;
import by.BackendTestTaskClevertec.service.UserService;
import by.BackendTestTaskClevertec.writer.AccountStatementWriter;
import by.BackendTestTaskClevertec.writer.CheckWriter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
public class FacadeServiceImpl implements FacadeService {

    private final BankAccountService accountService;

    private final BankService bankService;

    private final UserService userService;

    private final TransactionService transactionService;

    private final CheckWriter checkWriter;

    private final AccountStatementWriter statementWriter;

    @Override
    public Transaction moneyTransfer(BankAccount accountSender, BankAccount accountReceiver, BigDecimal moneyToSend) throws Exception {

        if (!accountReceiver.isActive()) {

            throw new Exception("Счёт: " + accountReceiver.getAccountNumber() + " заблокирован");

        }else if(accountSender.getAccountNumber().equals(accountReceiver.getAccountNumber())){

            throw new Exception("Вы не можете отправлять деньги сами себе");

        } else if (!(accountSender.getMoneyAmount().compareTo(moneyToSend) >= 0)) {

            throw new Exception("Недостаточно денег");

        } else if (!(moneyToSend.compareTo(BigDecimal.valueOf(Constant.MINIMUM_MONEY_AMOUNT_TO_SEND)) >= 0)) {

            throw new Exception("Минимальная сумма " + Constant.MINIMUM_MONEY_AMOUNT_TO_SEND + " " + accountSender.getCurrencyName());

        } else {

            accountSender.setMoneyAmount(accountSender
                    .getMoneyAmount().subtract(moneyToSend));

            Transaction createdTransaction = transactionService.create(new Transaction.TransactionBuilder()
                    .dateTime(new Timestamp(System.currentTimeMillis()))
                    .moneyAmount(moneyToSend)
                    .currency(accountSender.getCurrencyName())
                    .accountSenderNumber(accountSender.getAccountNumber())
                    .accountReceiverNumber(accountReceiver.getAccountNumber())
                    .build());

            accountService.update(accountSender);

            accountService.update(AddMoneyToReceiverAccount(accountReceiver, moneyToSend));

            return createdTransaction;
        }
    }

    @Override
    public String createAccountStatement(User user, String accountNumber, AccountStatementPeriod period) throws Exception {

        BankAccount bankAccount = accountService.getByNumber(accountNumber);

        if (bankAccount.getId() == 0){

            throw new IllegalArgumentException("Введён некорректный номер");
        }

        Bank bank = bankService.getById(bankAccount.getBankId());

        List<Transaction> allTransactions = transactionService.getAllTransactionsByPeriod(bankAccount, period);

        if (allTransactions.isEmpty()) {

            throw new Exception("За указанный период транзакций не было");
        }

        AccountStatement accountStatement = new AccountStatement(
                user,
                bankAccount,
                bank,
                new Timestamp(System.currentTimeMillis()),
                bankAccount.getMoneyAmount(),
                period,
                allTransactions);

        return statementWriter.write(accountStatement);
    }

    @Override
    public List<BankAccount> getAllBankAccountsByUser(User user) throws Exception {

        User newUser = userService.getById(user.getId());

        List<BankAccount> allAccountsByUserID = accountService.getAllByUserId(user.getId());

        if (newUser == null){

            throw new Exception("Такого пользователя не существует");
        } else if (allAccountsByUserID.isEmpty()){

            throw new Exception("Банковских счетов не нашлось");
        }

        return allAccountsByUserID;
    }

    @Override
    public Bank getBankByBankAccount(BankAccount account) throws Exception {

        if (account.getId() == 0){

            throw new Exception("Счёт не был найден");

        } else if(!account.isActive()){

            throw new Exception("Счёт заблокирован");
        }

        return bankService.getById(account.getBankId());
    }

    @Override
    public String writeCheck(Transaction transaction, Bank bankSender, Bank bankReceiver) {

        return checkWriter.write(transaction, bankSender.getName(), bankReceiver.getName());
    }

    @Override
    public BankAccount getBankAccountByNumber(String number) throws Exception {

        BankAccount accountByNumber = accountService.getByNumber(number);

        if (accountByNumber.getId() == 0){

            throw new Exception("Номер данного счёта не найден");
        }

        return accountByNumber;
    }

    private BankAccount AddMoneyToReceiverAccount(BankAccount receiverAccount, BigDecimal moneyToReceive) {

        BigDecimal addMoney = receiverAccount.getMoneyAmount().add(moneyToReceive);

        receiverAccount.setMoneyAmount(addMoney);

        return receiverAccount;
    }
}
