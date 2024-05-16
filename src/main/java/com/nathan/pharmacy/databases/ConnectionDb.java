package com.nathan.pharmacy.databases;

import java.sql.*;

public class ConnectionDb {
    private   Connection connection = null;
    private  Statement stat;

    public ConnectionDb(){};

    public   Connection connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        stat = connection.createStatement();
        return connection;
    }

    public   ResultSet executeQuery(String query) throws SQLException {
        return stat.executeQuery(query);
    }

    public void executeUpdateQuery(String query) throws SQLException{
        stat.executeUpdate(query);
    }

    public void close() throws SQLException {
        stat.close();
        connection.close();
    }
}
