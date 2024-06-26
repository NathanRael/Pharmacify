package com.nathan.pharmacy.controllers.patient;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.models.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PatientModelController implements ModelInterface<Patient> {

    private final ConnectionDb connection;

    public PatientModelController() {
        try {
            connection = ConnectionDb.getInstance();
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
        String query = String.format("UPDATE patient SET patientFName = '%s', patientLName = '%s', patientPhone = '%s', patientAddress = '%s', patientEmail = '%s' WHERE patientId = '%s'", purifyValue(patient.getFirstName()), purifyValue(patient.getLastName()), patient.getPhone(),purifyValue(patient.getAddress()), patient.getEmail(), patient.getId());

        connection.executeUpdateQuery(query);
    }

    @Override
    public int getCount() throws Exception {
        String query = "SELECT count(*) as len FROM patient";
        ResultSet rs = connection.executeQuery(query);
        rs.next();
        int ln = rs.getInt("len");
        return ln;
    }

    public ResultSet searchLike(String colName, String value) throws Exception{
        String query = "SELECT * FROM patient WHERE " + colName + " LIKE ?";
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
    public void delete(int id) throws Exception {
        String query = "DELETE FROM patient WHERE patientId = " + id;
        connection.executeUpdateQuery(query);
    }

    @Override
    public void insert(Patient patient) throws Exception {
        String query = String.format("INSERT INTO patient(patientFName, patientLName, patientPhone, patientAddress, patientEmail) VALUES ('%s', '%s', '%s', '%s', '%s' )", purifyValue(patient.getFirstName()), purifyValue(patient.getLastName()), patient.getPhone(),purifyValue(patient.getAddress()), patient.getEmail());
        connection.executeUpdateQuery(query);
    }

    @Override
    public void updateBy(Object... rows) throws Exception {
        StringBuilder query = new StringBuilder("UPDATE patient SET ");
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
