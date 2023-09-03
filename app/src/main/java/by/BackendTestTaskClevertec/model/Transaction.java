package by.BackendTestTaskClevertec.model;

import by.BackendTestTaskClevertec.model.documents.Documents;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Data
public class Transaction implements BankSystemModel, Documents {

    private long id;

    private Timestamp dateTime;

    private String currency;

    private String accountSenderNumber;

    private String accountReceiverNumber;

    private BigDecimal moneyAmount;

    public static class TransactionBuilder{

        private final Transaction transaction;

        public TransactionBuilder() {

            transaction = new Transaction();
        }

        public TransactionBuilder id(long id){
            transaction.id = id;
            return this;
        }

        public TransactionBuilder dateTime(Timestamp dateTime){
            transaction.dateTime = dateTime;
            return this;
        }

        public TransactionBuilder moneyAmount(BigDecimal moneyAmount){
            transaction.moneyAmount = moneyAmount;
            return this;
        }

        public TransactionBuilder accountSenderNumber(String accountSender){
            transaction.accountSenderNumber = accountSender;
            return this;
        }

        public TransactionBuilder accountReceiverNumber(String accountReceiver){
            transaction.accountReceiverNumber = accountReceiver;
            return this;
        }

        public TransactionBuilder currency(String currency){
            transaction.currency = currency;
            return this;
        }

        public Transaction build(){

            return transaction;
        }
    }
}
