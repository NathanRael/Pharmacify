package com.nathan.pharmacy.controllers.delivery;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.models.Delivery;

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
        String query = "SELECT delId, delPrice, delDate, delQuantity, s.supId, s.supName, m.medId, m.medName FROM delivery d, medicament m, supplier s WHERE d.supId = s.supId AND d.medId = m.medId";
        return connection.executeQuery(query);
    }

    @Override
    public ResultSet selectBy(String colName, String value) throws Exception {
        return null;
    }

    @Override
    public void update(Delivery obj) throws Exception {

    }

    @Override
    public int getCount() throws Exception {
        return 0;
    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public void insert(Delivery obj) throws Exception {

    }

    @Override
    public void updateBy(Object... rows) throws Exception {

    }

    @Override
    public void deleteBy(String colName, String value) throws Exception {

    }
}
