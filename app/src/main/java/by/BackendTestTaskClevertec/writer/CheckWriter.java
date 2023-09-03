package by.BackendTestTaskClevertec.writer;

import by.BackendTestTaskClevertec.model.Transaction;

public interface CheckWriter extends BankWriter {

    String write(Transaction transaction, String bankSenderName, String bankReceiverName);
}
