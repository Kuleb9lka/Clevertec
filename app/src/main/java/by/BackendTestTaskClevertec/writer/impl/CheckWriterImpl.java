package by.BackendTestTaskClevertec.writer.impl;

import by.BackendTestTaskClevertec.constant.Constant;
import by.BackendTestTaskClevertec.model.Transaction;
import by.BackendTestTaskClevertec.writer.CheckWriter;

import java.io.*;
import java.text.SimpleDateFormat;

public class CheckWriterImpl implements CheckWriter {

    public String write(Transaction transaction, String bankSenderName, String bankReceiverName) {

        File checkFile = new File(Constant.CHECKS_PATH + transaction.getId() + ".txt");

        String check = "";

        try(Writer checkWriter = new BufferedWriter(new FileWriter(checkFile))){

            check = ("""
                    Чек:                        %d
                    %s                  %s
                    Тип транзакции:             Перевод                                  
                    Банк отправителя:           %s
                    Банк получателя:            %s
                    Счёт отправителя:           %s
                    Счёт получателя:            %s
                    Сумма:                      %s %s
                    """);


            check = String.format(check,
                    transaction.getId(),
                    new SimpleDateFormat("dd-MM-yyyy").format(transaction.getDateTime()),
                    new SimpleDateFormat("HH:mm:ss").format(transaction.getDateTime()),
                    bankSenderName,
                    bankReceiverName,
                    transaction.getAccountSenderNumber(),
                    transaction.getAccountReceiverNumber(),
                    transaction.getMoneyAmount().toPlainString(),
                    transaction.getCurrency());

            checkWriter.write(check);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return check;
    }
}
