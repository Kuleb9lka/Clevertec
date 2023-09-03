package by.BackendTestTaskClevertec.repository.impl;

import by.BackendTestTaskClevertec.repository.connection.AbstractConnection;
import by.BackendTestTaskClevertec.model.BankAccount;
import by.BackendTestTaskClevertec.repository.BankAccountRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankAccountRepositoryImpl implements BankAccountRepository {

    @Override
    public BankAccount getById(long id) {

        PreparedStatement stmt;

        Connection connection = null;

        BankAccount bankAccount = new BankAccount();

        try {
            connection = AbstractConnection.getConnection();

            stmt = connection.prepareStatement("select * from public.bank_accounts where id=?");

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bankAccount.setId(rs.getLong("id"));
                bankAccount.setAccountNumber(rs.getString("number"));
                bankAccount.setBankId(rs.getLong("bank_id"));
                bankAccount.setAccountUserId(rs.getLong("user_id"));
                bankAccount.setDateOpen(rs.getTimestamp("date_open"));
                bankAccount.setCurrencyName(rs.getString("currency_name"));
                bankAccount.setMoneyAmount(rs.getBigDecimal("money_amount"));
                bankAccount.setActive(rs.getBoolean("is_active"));
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

        return bankAccount;
    }

    @Override
    public BankAccount create(BankAccount bankAccount) {

        Connection connection = null;
        PreparedStatement statement;

        long returnedId = 0;

        try {

            connection = AbstractConnection.getConnection();

            statement = connection.prepareStatement("insert into public.bank_accounts values (?, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, bankAccount.getAccountNumber());
            statement.setLong(2, bankAccount.getBankId());
            statement.setLong(3, bankAccount.getAccountUserId());
            statement.setTimestamp(4, bankAccount.getDateOpen());
            statement.setString(5, bankAccount.getCurrencyName());
            statement.setBigDecimal(6, bankAccount.getMoneyAmount());
            statement.setBoolean(7, bankAccount.isActive());

            statement.executeUpdate();

            try(ResultSet keys = statement.getGeneratedKeys()){

                if (keys.next()){

                    returnedId = keys.getLong("id");
                }
            }

            bankAccount.setId(returnedId);

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return bankAccount;
    }

    @Override
    public void update(BankAccount bankAccount) {

        Connection connection = null;

        PreparedStatement statement;

        try {

            connection = AbstractConnection.getConnection();

            statement = connection.prepareStatement("update public.bank_accounts set number=?, " +
                    "bank_id=?, " +
                    "user_id=?, " +
                    "date_open=?, " +
                    "currency_name=?, " +
                    "money_amount=?, " +
                    "is_active=? where id=?");

            statement.setString(1, bankAccount.getAccountNumber());
            statement.setLong(2, bankAccount.getBankId());
            statement.setLong(3, bankAccount.getAccountUserId());
            statement.setTimestamp(4, bankAccount.getDateOpen());
            statement.setString(5, bankAccount.getCurrencyName());
            statement.setBigDecimal(6, bankAccount.getMoneyAmount());
            statement.setBoolean(7, bankAccount.isActive());
            statement.setLong(8, bankAccount.getId());

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

            statement = connection.prepareStatement("delete from public.bank_accounts where id=?");

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
    public BankAccount getByNumber(String number) {

        PreparedStatement stmt;

        Connection connection = null;

        BankAccount bankAccount = new BankAccount();

        try {
            connection = AbstractConnection.getConnection();

            stmt = connection.prepareStatement("select * from public.bank_accounts where number=?");

            stmt.setString(1, number);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bankAccount.setId(rs.getLong("id"));
                bankAccount.setAccountNumber(rs.getString("number"));
                bankAccount.setBankId(rs.getLong("bank_id"));
                bankAccount.setAccountUserId(rs.getLong("user_id"));
                bankAccount.setDateOpen(rs.getTimestamp("date_open"));
                bankAccount.setCurrencyName(rs.getString("currency_name"));
                bankAccount.setMoneyAmount(rs.getBigDecimal("money_amount"));
                bankAccount.setActive(rs.getBoolean("is_active"));
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

        return bankAccount;
    }

    @Override
    public List<BankAccount> getAllByUserId(long id) {

        PreparedStatement stmt;

        Connection connection = null;

        List<BankAccount> bankAccounts = new ArrayList<>();

        try {
            connection = AbstractConnection.getConnection();

            stmt = connection.prepareStatement("select * from public.bank_accounts where user_id=?");

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                bankAccounts.add(new BankAccount.BankAccountBuilder()
                        .id(rs.getLong("id"))
                        .accountNumber(rs.getString("number"))
                        .bankId(rs.getLong("bank_id"))
                        .userId(rs.getLong("user_id"))
                        .dateOpen(rs.getTimestamp("date_open"))
                        .currency(rs.getString("currency_name"))
                        .moneyAmount(rs.getBigDecimal("money_amount"))
                        .isActive(rs.getBoolean("is_active"))
                        .build());
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

        return bankAccounts;
    }
}