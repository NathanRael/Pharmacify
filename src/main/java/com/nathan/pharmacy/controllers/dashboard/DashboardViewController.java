package com.nathan.pharmacy.controllers.dashboard;

import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.models.Medicament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {

    @FXML
    private ScrollPane scroll;

    @FXML
    private TableColumn<Medicament, String> colDesc;

    @FXML
    private TableColumn<Medicament, LocalDate> colExpireDate;

    @FXML
    private TableColumn<Medicament, String> colName;

    @FXML
    private TableColumn<Medicament, Float> colPrice;

    @FXML
    private TableColumn<Medicament, Integer> colQuantity;

    @FXML
    private TableColumn<Medicament, Integer> colId;
    @FXML
    private TableView<Medicament> tableMedicament;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableView();
        try {
            loadTableContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initTableView(){
        try{
            colId.setCellValueFactory(new PropertyValueFactory<Medicament, Integer>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<Medicament, String>("name"));
            colPrice.setCellValueFactory(new PropertyValueFactory<Medicament, Float>("price"));
            colDesc.setCellValueFactory(new PropertyValueFactory<Medicament, String>("desc"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<Medicament, Integer>("quantity"));
            colExpireDate.setCellValueFactory(new PropertyValueFactory<Medicament, LocalDate>("expDate"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void loadTableContent() throws Exception {
        ObservableList<Medicament> medicament = FXCollections.observableArrayList();
        MedicamentModelController mc = new MedicamentModelController();

        ResultSet rs = mc.selectAll();

        int i = 0;
        while (rs.next()){
            if (i >= 3) break;
            int medId = rs.getInt("medId");
            String medName = rs.getString("medName");
            String medDesc = rs.getString("medDesc");
            float medPrice =  rs.getFloat("medPrice");
            int medQuantity = rs.getInt("medQuantity");
            int stockId =  rs.getInt("stockId");
            LocalDate medExpDate = rs.getDate("medExpDate").toLocalDate();
            medicament.add(new Medicament(medId, medName, medDesc,medPrice, medQuantity,stockId, medExpDate));
            i++;
        }

        tableMedicament.setItems(medicament);
    }
}
