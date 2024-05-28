package com.nathan.pharmacy.controllers.medicament;

import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.models.Medicament;
import javafx.fxml.FXML;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicamentModelController implements  ModelInterface<Medicament>{
    public String[] tableRow = {""};
    private final ConnectionDb connection;

    public MedicamentModelController() throws Exception {
        connection = new ConnectionDb();
    }

    @Override
    public ResultSet selectAll() throws Exception {
        String query = "SELECT * FROM medicament";
        ResultSet rs = connection.executeQuery(query);
        return  rs;
    }

    @Override
    public ResultSet selectBy(String colName, String value) throws Exception{
        String query = String.format("SELECT * FROM medicament WHERE %s = '%s'", colName, value);
        ResultSet rs = connection.executeQuery(query);
        return  rs;
    }

    public void updateQuantity(int medQuantity, int medId) throws SQLException {
        String query = String.format("UPDATE medicament SET medQuantity = '%s' WHERE medId = '%s'", medQuantity, medId);
        connection.executeUpdateQuery(query);
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
    public int getCount() throws Exception{
        ResultSet rs = null;
        String query = "SELECT count(*) as len FROM user";
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
        String query = String.format("INSERT INTO medicament(medName, medDesc, medPrice, medQuantity, stockId, medExpDate) VALUES ('%s','%s','%s', '%s', '%s', '%s')", medicament.getName(), medicament.getDesc(), medicament.getPrice(), medicament.getQuantity(), medicament.getStockId(), medicament.getExpDate().toString());
        connection.executeUpdateQuery(query);
    }

    @Override
    public void deleteBy(String colName, String value) throws Exception {
        String query = String.format("DELETE * FROM medicament WHERE %s = %s", colName, value);
        connection.executeUpdateQuery(query);
    }

}

