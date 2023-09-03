package by.BackendTestTaskClevertec.service.impl;

import by.BackendTestTaskClevertec.model.BankAccount;
import by.BackendTestTaskClevertec.repository.BankAccountRepository;
import by.BackendTestTaskClevertec.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository repository;

    @Override
    public BankAccount getById(long id) {

        return repository.getById(id);
    }

    @Override
    public BankAccount create(BankAccount bankAccount) {

        return repository.create(bankAccount);
    }

    @Override
    public void update(BankAccount bankAccount) {

        repository.update(bankAccount);
    }

    @Override
    public void delete(long id) {

        repository.delete(id);
    }

    @SneakyThrows
    public BankAccount getByNumber(String accountNumber){

        return repository.getByNumber(accountNumber);
    }

    @Override
    public List<BankAccount> getAllByUserId(long id) {

        return repository.getAllByUserId(id);
    }
}
