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
        String query = String.format("SELECT * FROM purchase WHERE %s = %s", colName, value);
        ResultSet rs = connection.executeQuery(query);
        return  rs;
    }

    @Deprecated
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
    public void insert(Purchase purchase) throws Exception {
        String query = String.format("INSERT INTO purchase(purchaseDate, medId, patientId, totalPrice) VALUES ('%s','%s','%s', '%s')", purchase.getDate(), purchase.getMedId(), purchase.getPatientId(), purchase.getTotalPrice());
        connection.executeUpdateQuery(query);
    }

    @Override
    public void updateBy(Object... rows) throws Exception {
        StringBuilder query = new StringBuilder("UPDATE purchase SET ");
        for (int i = 0, j = i+1; i < rows.length; i +=2,j+=2){
            if (i == rows.length-2){
                query.append(" WHERE ").append(rows[i]).append(" = ").append(rows[j]);
                break;
            }
            query.append(rows[i]).append(" = ").append("'").append(rows[j]).append("'");
            if (i < rows.length - 4){
                query.append(",");
            }

        }
        connection.executeUpdateQuery(String.valueOf(query));
    }

    @Override
    public void deleteBy(String colName, String value) throws Exception {

    }
}
