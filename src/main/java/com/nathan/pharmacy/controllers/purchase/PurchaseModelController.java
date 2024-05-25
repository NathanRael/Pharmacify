package com.nathan.pharmacy.controllers.purchase;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.models.Purchase;

import java.sql.ResultSet;

public class PurchaseModelController implements ModelInterface<Purchase> {
    private ConnectionDb connection;

    public PurchaseModelController() throws Exception {
        connection = new ConnectionDb();
    }
    @Override
    public ResultSet selectAll() throws Exception {
        String query = "SELECT * FROM purchase";
        ResultSet rs = connection.executeQuery(query);
        return  rs;
    }

    @Override
    public ResultSet selectBy(String colName, String value) throws Exception {
        return null;
    }

    @Override
    public void update(Purchase obj) throws Exception {

    }

    @Override
    public int getCount() throws Exception {
        return 0;
    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public void insert(Purchase obj) throws Exception {

    }

    @Override
    public void deleteBy(String colName, String value) throws Exception {

    }
}
