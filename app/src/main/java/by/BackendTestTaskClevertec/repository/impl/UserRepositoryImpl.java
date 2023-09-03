package by.BackendTestTaskClevertec.repository.impl;

import by.BackendTestTaskClevertec.repository.connection.AbstractConnection;
import by.BackendTestTaskClevertec.model.User;
import by.BackendTestTaskClevertec.repository.UserRepository;

import java.sql.*;

public class UserRepositoryImpl implements UserRepository {


    @Override
    public User getById(long id) {

        PreparedStatement statement;

        Connection connection = null;

        User user = new User();

        try {
            connection = AbstractConnection.getConnection();

            statement = connection.prepareStatement("select * from public.users where id=?");

            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setPatronymic(rs.getString("patronymic"));
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
        return user;
    }

    @Override
    public User create(User user) {

        Connection connection = null;
        PreparedStatement statement;

        try{

            long returnedId = 0;

            connection = AbstractConnection.getConnection();

            statement = connection.prepareStatement("insert into public.users (name, surname, patronymic) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPatronymic());


            statement.executeUpdate();

            try(ResultSet keys = statement.getGeneratedKeys()){

                if (keys.next()){

                    returnedId = keys.getLong("id");
                }
            }

            user.setId(returnedId);

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    @Override
    public void update(User user) {

        Connection connection = null;

        PreparedStatement statement;

        try {

            connection = AbstractConnection.getConnection();

            statement = connection.prepareStatement("update public.users set " +
                    "name=?," +
                    "surname=?," +
                    "patronymic=? where id=?");

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPatronymic());

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

            statement = connection.prepareStatement("delete from public.users where id=?");

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
