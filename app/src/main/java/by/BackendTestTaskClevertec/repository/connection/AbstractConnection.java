package by.BackendTestTaskClevertec.repository.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractConnection {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");

            if (connection == null) {
                connection = DriverManager.getConnection(
                        ConnectionConstants.URL,
                        ConnectionConstants.USERNAME,
                        ConnectionConstants.PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}