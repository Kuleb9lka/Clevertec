package by.BackendTestTaskClevertec.service;

import by.BackendTestTaskClevertec.model.BankSystemModel;

public interface BankSystemService<T extends BankSystemModel>{

    T getById(long id);
    T create(T t);
    void update(T t);
    void delete(long id);

}
