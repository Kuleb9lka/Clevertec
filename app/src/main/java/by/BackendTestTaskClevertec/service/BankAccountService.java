package by.BackendTestTaskClevertec.service;

import by.BackendTestTaskClevertec.model.BankAccount;

import java.util.List;

public interface BankAccountService extends BankSystemService<BankAccount> {

    BankAccount getByNumber(String accountNumber);

    List<BankAccount> getAllByUserId(long id);

}
