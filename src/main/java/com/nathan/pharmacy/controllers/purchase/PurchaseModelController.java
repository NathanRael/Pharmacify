package com.nathan.pharmacy.controllers.purchase;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.models.Purchase;

import java.sql.ResultSet;
import java.time.LocalDate;

public class PurchaseModelController implements ModelInterface<Purchase> {
    private final ConnectionDb connection;

    public PurchaseModelController() throws Exception {
        connection = ConnectionDb.getInstance();
    }
    @Override
    public ResultSet selectAll() throws Exception {
        String query = "SELECT * FROM purchase";
        ResultSet rs = connection.executeQuery(query);
        return  rs;
    }

    @Override
    public ResultSet selectBy(String colName, String value) throws Exception {
        String query = String.format("SELECT * FROM purchase WHERE %s = '%s'", colName, purifyValue(value));
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
        String query = "DELETE FROM purchase WHERE purchaseId = " + id;
        connection.executeUpdateQuery(query);
    }

    @Override
    public void insert(Purchase purchase) throws Exception {
        String query = String.format("INSERT INTO purchase(purchaseDate, medId, patientId, totalPrice, purchaseQuantity) VALUES ('%s','%s','%s', '%s', '%s')", purchase.getDate().toString(), purchase.getMedId(), purchase.getPatientId(), purchase.getTotalPrice(), purchase.getQuantity());
        connection.executeUpdateQuery(query);
    }

    public ResultSet selectJoin() throws Exception {
        String query = "SELECT * FROM purchase p, medicament m, patient pa WHERE p.medId = m.medId AND pa.patientId = p.patientId";
        return connection.executeQuery(query);
    }

    public ResultSet selectMostPurchasedProduct(int Limit, String date) throws Exception{
        String query = "SELECT DISTINCT medName, sum(purchaseQuantity) as count FROM purchase p, medicament m WHERE p.medId = m.medId GROUP BY medName  AND p.purchaseDate ='"+  date + "' ORDER BY sum(purchaseQuantity) DESC LIMIT " + Limit;

        return connection.executeQuery(query);
    }

    public ResultSet selectDateBefore(LocalDate date) throws Exception {
        String query = "SELECT sum(totalPrice) as price,  purchaseDate FROM purchase  WHERE purchaseDate <= '" + date + "' GROUP BY purchaseDate LIMIT 7";
        return connection.executeQuery(query);
    }

    @Override
    public void updateBy(Object... rows) throws Exception {
        StringBuilder query = new StringBuilder("UPDATE purchase SET ");
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

    }
}
