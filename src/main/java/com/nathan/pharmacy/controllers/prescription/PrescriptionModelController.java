package com.nathan.pharmacy.controllers.prescription;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.models.Prescription;

import java.sql.ResultSet;

public class PrescriptionModelController implements ModelInterface<Prescription> {

    private final ConnectionDb connexion;

    public PrescriptionModelController(){
        try {
            connexion = ConnectionDb.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet selectAll() throws Exception {
        String query = "SELECT * FROM prescription";
        return connexion.executeQuery(query);
    }

    @Override
    public ResultSet selectBy(String colName, String value) throws Exception {
        String query = String.format("SELECT * FROM prescription WHERE %s = '%s'", colName, value);
        return connexion.executeQuery(query);
    }


    public ResultSet selectJoin() throws Exception{
        String query = "SELECT p.prescId, p.prescDate, p.prescDuration,p.prescDesc, pa.patientFName, pa.patientId FROM prescription p, patient pa WHERE p.patientId = pa.patientId";
        return connexion.executeQuery(query);
    }

    @Override
    public void update(Prescription obj) throws Exception {

    }

    @Override
    public int getCount() throws Exception {
        return 0;
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM prescription WHERE prescId = " + id;
        connexion.executeUpdateQuery(query);
    }

    @Override
    public void insert(Prescription prescription) throws Exception {
        String query = String.format("INSERT INTO prescription(prescDate, prescDuration, prescDesc, patientId) VALUES ('%s','%s', '%s', '%s')", prescription.getDate(), prescription.getDuration(), prescription.getDesc(), prescription.getPatientId());
        connexion.executeUpdateQuery(query);
    }

    @Override
    public void updateBy(Object... rows) throws Exception {

    }

    @Override
    public void deleteBy(String colName, String value) throws Exception {

    }
}
