package com.nathan.pharmacy.controllers.stock;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.models.Stock;

import java.sql.ResultSet;

public class StockModelController implements ModelInterface<Stock> {
    private final ConnectionDb connection;

    public StockModelController() throws Exception{
        connection = ConnectionDb.getInstance();
    }

    @Override
    public ResultSet selectAll() throws Exception {
        String query = "SELECT * FROM stock";
        return connection.executeQuery(query);
    }


    @Override
    public ResultSet selectBy(String colName, String value) throws Exception {
        String query = String.format("SELECT * FROM stock WHERE %s = '%s'", colName, value);
        return connection.executeQuery(query);
    }

    @Override
    public void update(Stock stock) throws Exception {
        String query = "UPDATE stock SET stockName = " + purifyValue(stock.getName()) + "WHERE stockId = " + stock.getId();
        connection.executeUpdateQuery(query);
    }

    @Override
    public int getCount() throws Exception {
        String query = "SELECT count(*) as len FROM stock";
        ResultSet rs = connection.executeQuery(query);
        rs.next();
        return rs.getInt("len");
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE * FROM stock WHERE stockId = " + id;
        connection.executeUpdateQuery(query);
    }

    @Override
    public void insert(Stock stock) throws Exception {
        String query = "INSERT INTO stock values(stockName) VALUES ( '"+  purifyValue(stock.getName()) + "')";
        connection.executeUpdateQuery(query);
    }

    @Override
    public void updateBy(Object... rows) throws Exception {

    }

    @Override
    public void deleteBy(String colName, String value) throws Exception {
        String query = String.format("DELETE * FROM stock WHERE %s = %s", colName, purifyValue(value));
        connection.executeUpdateQuery(query);
    }


}
