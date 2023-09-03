package by.BackendTestTaskClevertec.enums;

public enum TransactionType {

    INCOME("Зачисление"),
    OUTCOME("Счисление");

    private String value;

    TransactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
