package com.nathan.pharmacy.databases;

import java.sql.*;

public class ConnectionDb {
    private static ConnectionDb instance = null;
    private   Connection connection = null;
    private  Statement stat;

    private ConnectionDb() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        stat = connection.createStatement();
    };

    public static synchronized ConnectionDb getInstance(){
        if (instance == null){
            try {
                instance = new ConnectionDb();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

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

    public Connection getConnection(){
        return connection;
    }
}
