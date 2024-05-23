package com.nathan.pharmacy.controllers.user;


import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    private final String[] tableRow = {"userId", "userName", "userPhone", "userPwd", "stockId", "userStatus"};
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
        String query = String.format("SELECT * FROM user WHERE %s = %s", colName, value.trim());
        ResultSet res = connection.executeQuery(query);
        return  res;
    }

    public void Update(User user) throws SQLException {
        String query = String.format("UPDATE user SET userName = %s,  userPhone = %s, stockId = %s, userStatus = %s WHERE userId = %s",user.getName(), user.getPhone(), user.getStockId(), user.getstatus(), user.getId());
        connection.executeUpdateQuery(query);
    }

    public ResultSet selectAll() throws Exception{
        ResultSet rs = null;
        String query = "SELECT * FROM user";
        rs = connection.executeQuery(query);
        return rs;

    }

    public int getCount() throws Exception{
        ResultSet rs = null;
        String query = "SELECT count(*) as len FROM user";
        rs = connection.executeQuery(query);
        rs.next();
        int len = rs.getInt("len");
        return len;
    }

    public String[] getTableRows() {

        return tableRow;
    }
}
