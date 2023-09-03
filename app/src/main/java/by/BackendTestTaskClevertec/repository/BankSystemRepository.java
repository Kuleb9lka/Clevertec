package by.BackendTestTaskClevertec.repository;

import by.BackendTestTaskClevertec.model.BankSystemModel;

public interface BankSystemRepository<T extends BankSystemModel>{

    T getById(long id);
    T create (T t);
    void update(T t);
    void delete(long id);
}
