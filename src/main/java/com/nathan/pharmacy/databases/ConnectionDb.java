package com.nathan.pharmacy.databases;

import java.sql.*;

public class ConnectionDb {
    private   Connection connection = null;
    private  Statement stat;

    public ConnectionDb() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        stat = connection.createStatement();
    };

    public  ResultSet executeQuery(String query) throws Exception {
        ResultSet res = stat.executeQuery(query);
        return  res;
    }

    public void executeUpdateQuery(String query) throws SQLException{
        stat.executeUpdate(query);
    }

    public PreparedStatement prepareStatement(String query) throws  Exception{
        return connection.prepareStatement(query);
    }

    public void close() throws SQLException {
        stat.close();
        connection.close();
    }
}
