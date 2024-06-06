package com.nathan.pharmacy.controllers.medicament;

import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.models.Medicament;
import javafx.fxml.FXML;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MedicamentModelController implements  ModelInterface<Medicament>{
    public String[] tableRow = {""};
    private final ConnectionDb connection;

    public MedicamentModelController() throws Exception {
        connection = ConnectionDb.getInstance();
    }

    @Override
    public ResultSet selectAll() throws Exception {
        String query = "SELECT * FROM medicament";
        return connection.executeQuery(query);
    }

    public ResultSet selectAllAvailable() throws Exception{
        String query = "SELECT * FROM medicament WHERE medQuantity > 0";
        return connection.executeQuery(query);
    }

    public ResultSet selectAllDisponibleMed() throws Exception {
        String query = "SELECT * FROM medicament WHERE medQuantity > 0 ORDER BY medName ASC";
        ResultSet rs = connection.executeQuery(query);
        return  rs;
    }
    @Override
    public ResultSet selectBy(String colName, String value) throws Exception{
        String query = String.format("SELECT * FROM medicament WHERE %s = '%s' ", colName, value);
        ResultSet rs = connection.executeQuery(query);
        return  rs;
    }

    public void updateQuantity(int medQuantity, int medId) throws SQLException {
        String query = String.format("UPDATE medicament SET medQuantity = '%s' WHERE medId = '%s'", medQuantity, medId);
        connection.executeUpdateQuery(query);
    }

    public ResultSet getMedicamentWithLowStock(int threshold) throws Exception {
        String query =  "SELECT * FROM medicament WHERE medQuantity <= " + threshold;
        return connection.executeQuery(query);
    }

    @Override
    public void update(Medicament medicament) throws Exception {
        String query = String.format("UPDATE medicament SET medName = %s, medDesc = %s, medPrice = %f, medQuantity = %s, stockId = %s, medExpDate = %s WHERE medId = %s", medicament.getName(), medicament.getDesc(), medicament.getPrice(), medicament.getQuantity(), medicament.getStockId() ,medicament.getExpDate().toString(), medicament.getId());
        connection.executeUpdateQuery(query);
    }

    @Override
    public void updateBy(Object ...rows) throws Exception{
        StringBuilder query = new StringBuilder("UPDATE medicament SET ");
        for (int i = 0, j = i+1; i < rows.length; i +=2,j+=2){
            if (i == rows.length-2){
                query.append(" WHERE ").append(rows[i]).append(" = ").append("'").append(rows[j]).append("'");
                break;
            }
            query.append(rows[i]).append(" = ").append("'").append(rows[j]).append("'");
            if (i < rows.length - 4){
                query.append(",");
            }

        }
        System.out.println(query);
        connection.executeUpdateQuery(String.valueOf(query));
    }

    @Override
    public int getCount() throws Exception{
        ResultSet rs = null;
        String query = "SELECT count(*) as len FROM medicament";
        rs = connection.executeQuery(query);
        rs.next();
        int len = rs.getInt("len");
        return len;
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM medicament WHERE medId = " + id;
        connection.executeUpdateQuery(query);
    }

    @Override
    public void insert(Medicament medicament) throws Exception {
        String query = String.format("INSERT INTO medicament(medName, medDesc, medPrice, medQuantity, stockId, medExpDate) VALUES ('%s','%s','%s', '%s', '%s', '%s')", purifyValue(medicament.getName()), purifyValue(medicament.getDesc()), medicament.getPrice(), medicament.getQuantity(), medicament.getStockId(), medicament.getExpDate().toString());
        connection.executeUpdateQuery(query);
    }

    public ResultSet searchBy(Object ...rows) throws Exception {
        StringBuilder query = new StringBuilder("SELECT * FROM medicament WHERE ");
        for (int i = 0, j = i+1; i < rows.length; i +=2,j+=2){
            query.append(rows[i]).append(" LIKE ").append("'%").append(rows[j]).append("%'");
            if (i < rows.length - 4){
                query.append("OR");
            }

        }
        return connection.executeQuery(String.valueOf(query));
    }

    public ResultSet searchLike(String colName, String value) throws Exception{
        String query = "SELECT * FROM medicament WHERE " + colName + " LIKE ?";
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
    public void deleteBy(String colName, String value) throws Exception {
        String query = String.format("DELETE * FROM medicament WHERE %s = '%s'", colName, value);
        connection.executeUpdateQuery(query);
    }

    public void deleteMedicamentAt(String currentDate) throws SQLException {
        String query = "DELETE FROM medicament WHERE medExpDate <= ' " + currentDate + " '" ;
        connection.executeUpdateQuery(query);
    }

    public void deleteExpiredMedicament() throws SQLException {
        String query = "DELETE FROM medicament WHERE medExpDate <= CURDATE() " ;
        connection.executeUpdateQuery(query);
    }


}

