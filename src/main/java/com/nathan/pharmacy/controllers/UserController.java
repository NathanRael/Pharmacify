package com.nathan.pharmacy.controllers;


import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.models.User;

import java.sql.ResultSet;

public class UserController {

    private ConnectionDb connection;
    public UserController() throws Exception{
        connection =  new ConnectionDb();
    };

    public void insert(User user) throws Exception{
        String query = String.format("INSERT INTO user(userName, userPhone, userPwd ) VALUES ('%s', '%s', '%s' )"
                , user.getName(), user.getPhone(), user.getPwd());

        connection.executeUpdateQuery(query);
    }

    public ResultSet selectBy(String colName, String value) throws Exception{
        String query = String.format("SELECT * FROM user WHERE userName = %s", colName, value.trim());
        ResultSet res = connection.executeQuery(query);
        return  res;
    }


}
