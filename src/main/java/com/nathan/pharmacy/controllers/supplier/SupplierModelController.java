package com.nathan.pharmacy.controllers.supplier;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.models.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SupplierModelController implements ModelInterface<Supplier> {
    private final ConnectionDb connection;

    public SupplierModelController() {
        try {
            connection = ConnectionDb.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet selectAll() throws Exception {
        String query = "SELECT * FROM supplier";
        return connection.executeQuery(query);
    }

    @Override
    public ResultSet selectBy(String colName, String value) throws Exception {
        String query = String.format("SELECT * FROM supplier WHERE %s = '%s' ", colName, value);
        return connection.executeQuery(query);

    }

    public ResultSet selectFavoriteSupplier() throws Exception{
        String query = "SELECT MAX(delQuantity) , supName, supPhone, d.delQuantity FROM supplier s, delivery d WHERE s.supId = d.supId GROUP BY s.supId HAVING MAX(d.delQuantity) LIMIT 1;";
        return connection.executeQuery(query);
    }

    public ResultSet searchLike(String colName, String value) throws Exception {
        String query = "SELECT * FROM supplier WHERE " + colName + " LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try {
            preparedStatement.setString(1, "%" + value + "%");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }


    @Override
    public void update(Supplier supplier) throws Exception {
        String query = String.format("UPDATE supplier SET supName = '%s', supPhone = '%s' WHERE supId = '%s'", purifyValue(supplier.getName()), supplier.getPhone(), supplier.getId());
        connection.executeUpdateQuery(query);
    }

    @Override
    public int getCount() throws Exception {
        String query = "SELECT COUNT(*) AS len FROM supplier";
        ResultSet rs = connection.executeQuery(query);
        rs.next();
        return rs.getInt("len");
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE  FROM supplier WHERE supId = " + id;
        connection.executeUpdateQuery(query);
    }

    @Override
    public void insert(Supplier supplier) throws Exception {
        String query = String.format("INSERT INTO supplier(supName, supPhone) VALUES ('%s', '%s')", purifyValue(supplier.getName()), supplier.getPhone());
        connection.executeUpdateQuery(query);
    }

    @Deprecated
    public void updateBy(Object... rows) throws Exception {
    }

    @Override
    public void deleteBy(String colName, String value) throws Exception {

    }

}
