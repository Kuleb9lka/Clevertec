package by.BackendTestTaskClevertec.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Data
public class BankAccount implements BankSystemModel {

    private long id;

    private String accountNumber;

    private long bankId;

    private long accountUserId;

    private Timestamp dateOpen;

    private String currencyName;

    private BigDecimal moneyAmount;

    private boolean isActive;

    public static class BankAccountBuilder {

        private final BankAccount bankAccount;

        public BankAccountBuilder() {

            bankAccount = new BankAccount();
        }

       public BankAccountBuilder id(long id){
            bankAccount.id = id;
            return this;
       }

       public BankAccountBuilder accountNumber(String accountNumber){
            bankAccount.accountNumber = accountNumber;
            return this;
       }

        public BankAccountBuilder userId(long userId){
            bankAccount.accountUserId = userId;
            return this;
        }

        public BankAccountBuilder bankId(long bankId){
            bankAccount.bankId = bankId;
            return this;
        }
        public BankAccountBuilder accountUserId(long userId){
            bankAccount.accountUserId = userId;
            return this;
        }

        public BankAccountBuilder dateOpen(Timestamp dateOpen){
            bankAccount.dateOpen = dateOpen;
            return this;
        }

        public BankAccountBuilder currency(String currencyName){
            bankAccount.currencyName = currencyName;
            return this;
        }

        public BankAccountBuilder moneyAmount(BigDecimal moneyAmount){
            bankAccount.moneyAmount = moneyAmount;
            return this;
        }

        public BankAccountBuilder isActive(boolean isActive){
            bankAccount.isActive = isActive;
            return this;
        }

        public BankAccount build(){

            return bankAccount;
        }
    }

}
