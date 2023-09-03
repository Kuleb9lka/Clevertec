package by.BackendTestTaskClevertec.repository.impl;

import by.BackendTestTaskClevertec.repository.connection.AbstractConnection;
import by.BackendTestTaskClevertec.model.Bank;
import by.BackendTestTaskClevertec.repository.BankRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankRepositoryImpl implements BankRepository {

    @Override
    public Bank getById(long id) {

        PreparedStatement stmt;
        Connection connection = null;
        Bank bank = new Bank();

        try {
            connection = AbstractConnection.getConnection();

            stmt = connection.prepareStatement("select * from public.banks where id=?");

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                bank.setId(rs.getLong("id"));
                bank.setName(rs.getString("name"));
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

        return bank;
    }

    @Override
    public Bank create(Bank bank) {

        Connection connection = null;
        PreparedStatement statement;

        long returnedId = 0;

        try {

            connection = AbstractConnection.getConnection();

            statement = connection.prepareStatement("insert into public.banks (name) values ?)");

            statement.setString(1, bank.getName());

            statement.executeUpdate();

            try(ResultSet keys = statement.getGeneratedKeys()){

                if (keys.next()){

                    returnedId = keys.getLong("id");
                }
            }

            bank.setId(returnedId);

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return bank;
    }

    @Override
    public void update(Bank bank) {

        Connection connection = null;
        PreparedStatement statement;

        try {

            connection = AbstractConnection.getConnection();

            statement = connection.prepareStatement("update public.banks set name=? where id=?");

            statement.setString(1, bank.getName());
            statement.setLong(2, bank.getId());

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

            statement = connection.prepareStatement("delete from public.banks where id=?");

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
}