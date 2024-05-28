package com.nathan.pharmacy.controllers.patient;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.models.Patient;

import java.sql.ResultSet;

public class PatientModelController implements ModelInterface<Patient> {

    private final ConnectionDb connection;

    public PatientModelController() {
        try {
            connection = new ConnectionDb();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet selectAll() throws Exception {
        String query = "SELECT * FROM patient";
        return connection.executeQuery(query);
    }

    @Override
    public ResultSet selectBy(String colName, String value) throws Exception {
        String query = String.format("SELECT * FROM patient WHERE %s = '%s' ", colName, value);
        return connection.executeQuery(query);
    }

    @Override
    public void update(Patient patient) throws Exception {
        String query = String.format("UPDATE patient SET patientFName = '%s', patientLName = '%s', patientPhone = '%s', patientAddress = '%s', patientEmail = '%s' WHERE patientId = '%s'", patient.getFirstName(), patient.getLastName(), patient.getPhone(),patient.getAddress(), patient.getEmail(), patient.getId());

        connection.executeUpdateQuery(query);
    }

    @Override
    public int getCount() throws Exception {
        return 0;
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM patient WHERE patientId = " + id;
        connection.executeUpdateQuery(query);
    }

    @Override
    public void insert(Patient patient) throws Exception {
        String query = String.format("INSERT INTO patient(patientFName, patientLName, patientPhone, patientAddress, patientEmail) VALUES ('%s', '%s', '%s', '%s', '%s' )", patient.getFirstName(), patient.getLastName(), patient.getPhone(),patient.getAddress(), patient.getEmail());
        connection.executeUpdateQuery(query);
    }

    @Override
    public void updateBy(Object... rows) throws Exception {
        StringBuilder query = new StringBuilder("UPDATE patient SET ");
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
