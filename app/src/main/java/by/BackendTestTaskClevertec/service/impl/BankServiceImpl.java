package by.BackendTestTaskClevertec.service.impl;

import by.BackendTestTaskClevertec.model.Bank;
import by.BackendTestTaskClevertec.repository.BankRepository;
import by.BackendTestTaskClevertec.service.BankService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository repository;

    @Override
    public Bank getById(long id) {

        return repository.getById(id);
    }

    @Override
    public Bank create(Bank bank) {

        return repository.create(bank);
    }

    @Override
    public void update(Bank bank) {

        repository.create(bank);
    }

    @Override
    public void delete(long id) {

        repository.delete(id);
    }
}
