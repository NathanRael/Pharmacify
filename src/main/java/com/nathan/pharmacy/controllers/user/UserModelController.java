package com.nathan.pharmacy.controllers.user;


import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.models.User;

import java.sql.ResultSet;

public class UserModelController implements ModelInterface<User> {

    private final String[] tableRow = {"userId", "userName", "userPhone", "userPwd", "stockId", "userStatus"};
    private final ConnectionDb connection;
    public UserModelController() throws Exception{
        connection =  ConnectionDb.getInstance();
    };

    @Override
    public void insert(User user) throws Exception{
        String query = String.format("INSERT INTO user(userName, userPhone, userPwd, userEmail) VALUES ('%s', '%s', '%s', '%s' )"
                , user.getName(), user.getPhone(), user.getPwd(), user.getEmail());
        connection.executeUpdateQuery(query);
    }

    public  ResultSet searchJoin(String colName, String value) throws Exception {
        String query = String.format("SELECT * FROM user u, stock s WHERE u.stockId = s.stockId AND %s = '%s'", colName, value.trim());
        ResultSet res = connection.executeQuery(query);
        return  res;
    }


    @Override
    public void updateBy(Object... rows) throws Exception {
        StringBuilder query = new StringBuilder("UPDATE user SET ");
        for (int i = 0, j = i+1; i < rows.length; i +=2,j+=2){
            if (i == rows.length-2){
                query.append(" WHERE ").append(rows[i]).append(" = ").append(purifyValue(rows[j]));
                break;
            }
            query.append(rows[i]).append(" = ").append("'").append(purifyValue(rows[j])).append("'");
            if (i < rows.length - 4){
                query.append(",");
            }

        }
        connection.executeUpdateQuery(String.valueOf(query));
    }

    @Override
    public void deleteBy(String colName, String value) throws Exception {
        String query = String.format("DELETE * FROM user WHERE %s = '%s' ", colName, purifyValue(value));
        connection.executeUpdateQuery(query);
    }

    public ResultSet selectJoin() throws Exception {
        String query = "SELECT * FROM user u, stock s WHERE u.stockId = s.stockId";
        return connection.executeQuery(query);
    }


    @Override
    public ResultSet selectBy(String colName, String value) throws Exception{
        String query = String.format("SELECT * FROM user WHERE %s = '%s'", colName, value.trim());
        ResultSet res = connection.executeQuery(query);
        return  res;
    }

    @Override
    public void update(User user) throws Exception {
        String query = String.format("UPDATE user SET userName = '%s',  userPhone = '%s', stockId = '%s', userStatus = '%s' WHERE userId = '%s'",purifyValue(user.getName()), user.getPhone(), user.getStockId(), user.getStatus(), user.getId());
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

    @Override
    public void delete(int id) throws Exception {

    }

    public String[] getTableRows() {

        return tableRow;
    }
}
