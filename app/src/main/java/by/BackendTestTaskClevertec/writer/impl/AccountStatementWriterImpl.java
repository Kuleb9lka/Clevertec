package by.BackendTestTaskClevertec.writer.impl;

import by.BackendTestTaskClevertec.constant.Constant;
import by.BackendTestTaskClevertec.enums.AccountStatementPeriod;
import by.BackendTestTaskClevertec.model.Bank;
import by.BackendTestTaskClevertec.model.BankAccount;
import by.BackendTestTaskClevertec.model.Transaction;
import by.BackendTestTaskClevertec.model.User;
import by.BackendTestTaskClevertec.model.documents.AccountStatement;
import by.BackendTestTaskClevertec.writer.AccountStatementWriter;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountStatementWriterImpl implements AccountStatementWriter {



    @Override
    public String write(AccountStatement statement) {

        String statementText = "";

        Bank bank = statement.getBank();
        BankAccount bankAccount = statement.getBankAccount();
        User user = statement.getUser();
        LocalDateTime end = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
        LocalDateTime start;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedStatement = "";

        File statementFile = new File(Constant.ACCOUNT_STATEMENTS_PATH + user.getId() + "_" +
                new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(statement.getDateTime()) + ".txt");

        try(Writer statementWriter = new BufferedWriter(new FileWriter(statementFile))){

            if (statement.getPeriod().equals(AccountStatementPeriod.WHOLE_PERIOD)){

                start = statement.getBankAccount().getDateOpen().toLocalDateTime();
            } else {

                start = end.minusDays(statement.getPeriod().getDaysOfPeriod());
            }


            statementText = ("""
                                        Выписка
                                          %s
                Клиент                      | %s %s %s                                          
                Счёт                        | %s
                Валюта                      | %s
                Дата открытия               | %s
                Период                      | %s - %s
                Дата и время формирования   | %s
                Остаток                     | %s %s
                  Дата    |      Примечание      |   Сумма            
                ----------------------------------------------
                """);

            formattedStatement = String.format(statementText,
                    bank.getName(),
                    user.getSurname(), user.getName(), user.getPatronymic(),
                    bankAccount.getAccountNumber(),
                    bankAccount.getCurrencyName(),
                    new SimpleDateFormat("dd-MM-yyyy").format(bankAccount.getDateOpen()),
                    start.format(format),
                    end.format(format),
                    new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss").format(statement.getDateTime()),
                    bankAccount.getMoneyAmount().toPlainString(), bankAccount.getCurrencyName());

            String formattedTransactions = "";

            for (Transaction transaction : statement.getListOfTransactions()) {

                String transactionString = "%s|      %s          %s%s";

                String incomeOutcome = "";
                String plusOrMinus = "";
                if (transaction.getAccountReceiverNumber().equals(bankAccount.getAccountNumber())){

                    plusOrMinus = "+";
                    incomeOutcome = "Зачисление";
                } else {

                    plusOrMinus = "-";
                    incomeOutcome = "Списание";
                }

                 formattedTransactions += String.format(transactionString,
                        transaction.getDateTime().toLocalDateTime().format(format),
                        incomeOutcome,
                        plusOrMinus,
                        transaction.getMoneyAmount().toPlainString()) + "\n";
            }

            formattedStatement += formattedTransactions;

            statementWriter.write(formattedStatement);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return formattedStatement;
    }
}
