package by.BackendTestTaskClevertec.model;


import lombok.Data;

@Data
public class User implements BankSystemModel {

    private long id;

    private String name;

    private String surname;

    private String patronymic;

}
