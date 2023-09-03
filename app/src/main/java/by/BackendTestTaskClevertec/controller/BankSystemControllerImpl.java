package by.BackendTestTaskClevertec.controller;

import by.BackendTestTaskClevertec.exception.ExceptionHandler;
import by.BackendTestTaskClevertec.enums.AccountStatementPeriod;
import by.BackendTestTaskClevertec.model.Bank;
import by.BackendTestTaskClevertec.model.BankAccount;
import by.BackendTestTaskClevertec.model.Transaction;
import by.BackendTestTaskClevertec.model.User;
import by.BackendTestTaskClevertec.writer.ConsoleWriter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class BankSystemControllerImpl implements BankSystemController{

    private final Scanner userInput = new Scanner(System.in);

    private final ExceptionHandler exceptionHandler;

    private final ConsoleWriter consoleWriter;

    private User currentUser;

    private BankAccount currentBankAccount;

    @Override
    public void start(User user) {

        this.currentUser = user;

        consoleWriter.write("Вы вошли как: " + user.getSurname() + " " + user.getName() + " " + user.getPatronymic());

        while (true) {

            showStartMenu();

            String mainScan = userInput.nextLine();

            switch (mainScan){

                case "1":

                    bankAccountsMenu();

                    break;

                case "2":

                    sendMoneyMenu();

                    break;

                case "3":

                    accountStatementMenu();

                    break;

                case "4":

                    return;
                default:
                    consoleWriter.write("Введите корректные данные");
            }
        }
    }

    private void bankAccountsMenu(){

        BankAccount bankAccount = setCurrentBankAccount();

        if (bankAccount == null) return;

        Bank bank = exceptionHandler.getBankByBankAccount(bankAccount);
        consoleWriter.write("\nНомер счёта: " + bankAccount.getAccountNumber());
        consoleWriter.write("Банк: " + bank.getName());
        consoleWriter.write("Дата открытия: " + bankAccount.getDateOpen().toLocalDateTime().toLocalDate());
        consoleWriter.write("Остаток на счёте: " + bankAccount.getMoneyAmount() + " " + bankAccount.getCurrencyName() + "\n");
    }

    private void sendMoneyMenu(){

        BankAccount bankAccount = setCurrentBankAccount();

        if (bankAccount == null) return;

        consoleWriter.write("Введите номер кому отправляем");

        String accountNumber = userInput.nextLine();

        BankAccount accountReceiver = exceptionHandler.getBankAccountByNumber(accountNumber);

        if (accountReceiver == null){

            return;
        }

        consoleWriter.write("Введите сумму");

        String moneyToSend = userInput.nextLine();

        if (!isCorrectMoneyAmount(moneyToSend)){

            consoleWriter.write("Проверьте правильность введенных данных. Допускается использовать числа и точку.");

            return;
        }

        Transaction transaction = exceptionHandler.moneyTransfer(currentBankAccount, accountReceiver, new BigDecimal(moneyToSend));

        Bank sender = exceptionHandler.getBankByBankAccount(currentBankAccount);

        Bank receiver = exceptionHandler.getBankByBankAccount(accountReceiver);

        consoleWriter.write(exceptionHandler.writeCheck(transaction, sender, receiver));
    }

    private void accountStatementMenu() {

        setCurrentBankAccount();

        Map<Integer, AccountStatementPeriod> allPeriods = getAllPeriods();

        String statementPeriod = userInput.nextLine();

        AccountStatementPeriod period = allPeriods.get(Integer.valueOf(statementPeriod));

        if (period == null){

            consoleWriter.write("Указан несуществующий период");
            return;
        }

        consoleWriter.write(exceptionHandler.createAccountStatement(currentUser, currentBankAccount.getAccountNumber(), period));
    }

    private Map<Integer, AccountStatementPeriod> getAllPeriods(){

        Map<Integer, AccountStatementPeriod> periodsMap = new HashMap<>();

        AccountStatementPeriod[] periodsArray = AccountStatementPeriod.values();

        for (int i = 0; i < periodsArray.length; i++) {

            periodsMap.put(i + 1, periodsArray[i]);
        }

        consoleWriter.write("Выберете период выписки");

        for (Map.Entry<Integer, AccountStatementPeriod> entry: periodsMap.entrySet()) {

            consoleWriter.write(entry.getKey() + ". " + entry.getValue().getPeriodName());
        }

        return periodsMap;
    }

    private Map<Long, BankAccount> getAllBankAccounts(){

        Map<Long, BankAccount> accountsMap = new HashMap<>();

        List<BankAccount> list = exceptionHandler.getAllBankAccountsByUser(currentUser);

        for (int i = 0; i < list.size(); i++) {

            accountsMap.put((long)i + 1, list.get(i));
        }

        consoleWriter.write("Выберете ваш счёт");

        for (Map.Entry<Long, BankAccount> entry: accountsMap.entrySet()) {

            consoleWriter.write(entry.getKey() + ". " + entry.getValue().getAccountNumber());
        }

        return accountsMap;
    }

    private BankAccount setCurrentBankAccount(){

        Map<Long, BankAccount> accounts = getAllBankAccounts();

        String scan = userInput.nextLine();

        BankAccount bankAccount = accounts.get(Long.valueOf(scan));

        if (bankAccount.isActive()){

            this.currentBankAccount = bankAccount;
        } else {

            consoleWriter.write("Данный аккаунт заблокирован");
            return null;
        }
        return bankAccount;
    }

    private void showStartMenu(){

        consoleWriter.write("""
                1. Просмотреть состояние счёта
                2. Отправить деньги по номеру счёта
                3. Получить выписку
                4. Выход
                """);
    }

    private boolean isCorrectMoneyAmount(String bigDecimal){

        String regex = "\\d+[.]?\\d{0,2}?";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(bigDecimal);

        return matcher.matches();
    }

}
