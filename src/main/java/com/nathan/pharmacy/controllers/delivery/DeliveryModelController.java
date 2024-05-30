package com.nathan.pharmacy.controllers.delivery;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.models.Delivery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeliveryModelController implements ModelInterface<Delivery> {

    private ConnectionDb connection;

    public DeliveryModelController(){
        try {
            connection = new ConnectionDb();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public ResultSet selectAll() throws Exception {
        String query = "SELECT * FROM delivery";
        return connection.executeQuery(query);
    }

    public ResultSet selectJoin() throws Exception{
        String query = "SELECT d.delId, d.delPrice, d.delDate, d.delQuantity, s.supId, s.supName, m.medId, m.medName, m.medExpDate FROM delivery d, medicament m, supplier s WHERE d.supId = s.supId AND d.medId = m.medId";
        return connection.executeQuery(query);
    }

    public ResultSet searchLike(String colName, String value) throws Exception{
        String query = "SELECT * FROM delivery d INNER JOIN medicament m ON m.medId = d.medId INNER JOIN supplier s ON s.supId = d.supId WHERE " + colName + " LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try{
            preparedStatement.setString(1, "%" + value + "%");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        ResultSet rs = preparedStatement.executeQuery();
        return  rs;
    }

    @Override
    public ResultSet selectBy(String colName, String value) throws Exception {
        return null;
    }

    @Override
    public void update(Delivery delivery) throws Exception {

    }

    @Override
    public int getCount() throws Exception {
        return 0;
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM delivery WHERE delId = " + id;
        connection.executeUpdateQuery(query);
    }

    @Override
    public void insert(Delivery delivery) throws Exception {
        String query = String.format("INSERT INTO delivery(delPrice, delDate, supId, medId, delQuantity) VALUES ('%s', '%s', '%s', '%s', '%s')", delivery.getPrice(), delivery.getDate().toString(), delivery.getSupId(), delivery.getMedId(), delivery.getQuantity());
        connection.executeUpdateQuery(query);
    }

    @Override
    public void updateBy(Object... rows) throws Exception {

    }

    @Override
    public void deleteBy(String colName, String value) throws Exception {

    }
}
