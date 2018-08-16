package main.Services;

import java.sql.Connection;

public interface IDataBase {
    Connection getConnection();
    void closeConnection(Connection connection);
}
