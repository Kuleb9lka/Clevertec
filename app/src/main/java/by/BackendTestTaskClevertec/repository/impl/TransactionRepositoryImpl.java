package by.BackendTestTaskClevertec.repository.impl;

import by.BackendTestTaskClevertec.repository.connection.AbstractConnection;
import by.BackendTestTaskClevertec.enums.AccountStatementPeriod;
import by.BackendTestTaskClevertec.model.Transaction;
import by.BackendTestTaskClevertec.repository.TransactionRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository {

    @Override
    public Transaction getById(long id) {

        PreparedStatement stmt;
        Connection connection = null;
        Transaction transaction = new Transaction();

        try {
            connection = AbstractConnection.getConnection();

            stmt = connection.prepareStatement("select * from public.transactions where id=?");

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                transaction.setId(rs.getLong("id"));
                transaction.setCurrency(rs.getString("currency"));
                transaction.setDateTime(rs.getTimestamp("date_time"));
                transaction.setMoneyAmount(rs.getBigDecimal("money_amount"));
                transaction.setAccountSenderNumber(rs.getString("sender_account_number"));
                transaction.setAccountReceiverNumber(rs.getString("receiver_account_number"));
            }

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transaction;
    }

    @Override
    public Transaction create(Transaction transaction) {

        Connection connection = null;
        PreparedStatement statement;
        long returnedId = 0;

        try {

            connection = AbstractConnection.getConnection();

            statement = connection.prepareStatement("insert into public.transactions(" +
                    "date_time, " +
                    "money_amount, " +
                    "currency_name, " +
                    "sender_account_number, " +
                    "receiver_account_number) " +
                            "values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            statement.setTimestamp(1, transaction.getDateTime());
            statement.setBigDecimal(2, transaction.getMoneyAmount());
            statement.setString(3, transaction.getCurrency());
            statement.setString(4, transaction.getAccountSenderNumber());
            statement.setString(5, transaction.getAccountReceiverNumber());

            statement.executeUpdate();

            try(ResultSet keys = statement.getGeneratedKeys()){

                if (keys.next()){

                    returnedId = keys.getLong("id");
                }
            }

            transaction.setId(returnedId);

        } catch (SQLException e) {
            e.getMessage();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transaction;
    }

    @Override
    public void update(Transaction transaction) {

        Connection connection = null;
        PreparedStatement statement;

        try {

            connection = AbstractConnection.getConnection();

            statement = connection.prepareStatement("update public.transactions set date_time=?, " +
                    "money_amount=? " +
                    "currency=?, " +
                    "sender_account_number=?, " +
                    "receiver_account_number=? where id=?");

            statement.setTimestamp(1, transaction.getDateTime());
            statement.setBigDecimal(2, transaction.getMoneyAmount());
            statement.setString(3, transaction.getCurrency());
            statement.setString(4, transaction.getAccountSenderNumber());
            statement.setString(5, transaction.getAccountReceiverNumber());
            statement.setLong(6, transaction.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(long id) {

        Connection connection = null;
        PreparedStatement statement;

        try {

            connection = AbstractConnection.getConnection();

            statement = connection.prepareStatement("delete from public.transactions where id=?");

            statement.setLong(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public List<Transaction> getAllTransactionsForPeriod(String accountNumber, AccountStatementPeriod period) {

        List<Transaction> listOfTransactions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;

        try {

            connection = AbstractConnection.getConnection();

            LocalDateTime flexiblePeriod = LocalDateTime.now().minusDays(period.getDaysOfPeriod());


            if (period.equals(AccountStatementPeriod.WHOLE_PERIOD)){

                statement = connection.prepareStatement("select * from public.transactions " +
                        "where sender_account_number=? or receiver_account_number=?");

            } else {

                statement = connection.prepareStatement("select * from public.transactions " +
                        "where sender_account_number=? or receiver_account_number=? and date_time >= ?");

                statement.setTimestamp(3, Timestamp.valueOf(flexiblePeriod));
            }
            statement.setString(1, accountNumber);
            statement.setString(2, accountNumber);


            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                listOfTransactions.add(new Transaction.TransactionBuilder()
                        .id(rs.getLong("id"))
                        .dateTime(rs.getTimestamp("date_time"))
                        .currency(rs.getString("currency_name"))
                        .moneyAmount(rs.getBigDecimal("money_amount"))
                        .accountSenderNumber(rs.getString("sender_account_number"))
                        .accountReceiverNumber(rs.getString("receiver_account_number"))
                        .build());
            }

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return listOfTransactions;
    }
}
