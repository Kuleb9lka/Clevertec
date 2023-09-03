package by.BackendTestTaskClevertec.repository;

import by.BackendTestTaskClevertec.model.BankAccount;

import java.util.List;

public interface BankAccountRepository extends BankSystemRepository<BankAccount> {

    BankAccount getByNumber(String number);

    List<BankAccount> getAllByUserId(long id);
}
