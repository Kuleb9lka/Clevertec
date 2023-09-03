package by.BackendTestTaskClevertec.enums;

public enum AccountStatementPeriod {

    MONTH("One month", 30),
    YEAR("One year", 365),
    WHOLE_PERIOD("Whole period", -1);

    String periodName;
    long daysOfPeriod;

    AccountStatementPeriod(String periodName, long daysOfPeriod) {
        this.periodName = periodName;
        this.daysOfPeriod = daysOfPeriod;
    }

    public String getPeriodName() {
        return periodName;
    }

    public long getDaysOfPeriod() {
        return daysOfPeriod;
    }
}
