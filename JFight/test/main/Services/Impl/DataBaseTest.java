package main.Services.Impl;

import main.Services.IDataBase;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class DataBaseTest {
    IDataBase dataBase = new DataBase();

    @Test
    public void getConnection() throws SQLException {
        Connection con = dataBase.getConnection();
        assertTrue(!con.isClosed());
        con.isClosed();
    }

    @Test
    public void closeConnection() throws SQLException {
        Connection con = dataBase.getConnection();
        dataBase.closeConnection(con);
        assertTrue(con.isClosed());
    }
}