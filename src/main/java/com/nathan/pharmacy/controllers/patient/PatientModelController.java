package com.nathan.pharmacy.controllers.patient;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.models.Patient;

import java.sql.ResultSet;

public class PatientModelController implements ModelInterface<Patient> {

    private ConnectionDb connection;

    public PatientModelController() {
        try {
            connection = new ConnectionDb();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet selectAll() throws Exception {
        return null;
    }

    @Override
    public ResultSet selectBy(String colName, String value) throws Exception {
        String query = String.format("SELECT * FROM patient WHERE %s = %s ", colName, value);
        return connection.executeQuery(query);
    }

    @Override
    public void update(Patient obj) throws Exception {

    }

    @Override
    public int getCount() throws Exception {
        return 0;
    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public void insert(Patient obj) throws Exception {

    }

    @Override
    public void updateBy(Object... rows) throws Exception {

    }

    @Override
    public void deleteBy(String colName, String value) throws Exception {

    }
}
