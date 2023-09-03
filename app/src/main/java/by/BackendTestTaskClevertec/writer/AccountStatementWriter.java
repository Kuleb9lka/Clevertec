package by.BackendTestTaskClevertec.writer;

import by.BackendTestTaskClevertec.model.documents.AccountStatement;

public interface AccountStatementWriter extends BankWriter {

    String write(AccountStatement statement);


}
