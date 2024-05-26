package com.nathan.pharmacy.controllers.purchase;

import com.nathan.pharmacy.controllers.form.IsValidFields;
import com.nathan.pharmacy.controllers.form.ValidNumber;
import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.Purchase;
import com.nathan.pharmacy.models.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PurchaseViewController implements Initializable {

    @FXML
    private Button btnPurchase;
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
    private TextField inputTotalPrice;

    @FXML
    private TextField inputMedName;

    @FXML
    private TextField inputMedQuantity;

    @FXML
    private TextField inputPatientId;

    @FXML
    private TextField inputPatientName;

    @FXML
    private ChoiceBox<String> selectMedFilter;

    @FXML
    private TableView<Medicament> tableMedicament;

    private List<Medicament> currSelectedMedRow = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectMedFilter.getItems().addAll("Prix", "Expiration", "Test");
        initTableView();
        try {
            loadTableContent();
            tableMedicament.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
                if (newSelection != null){
                   handleMedRowSelected(newSelection);
                }
            });
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

        while (rs.next()){
            int medId = rs.getInt("medId");
            String medName = rs.getString("medName");
            String medDesc = rs.getString("medDesc");
            float medPrice =  rs.getFloat("medPrice");
            int medQuantity = rs.getInt("medQuantity");
            int stockId =  rs.getInt("stockId");
            Date medExpDate = (Date) rs.getObject("medExpDate");
            medicament.add(new Medicament(medId, medName, medDesc,medPrice, medQuantity,stockId, medExpDate));
        }

        tableMedicament.setItems(medicament);
    }


    public void purchaseMedicament(){

    }
    private void setFieldsValue(Medicament currentSelection){
        String medName = currentSelection.getName();
        inputMedName.setText(medName);
        setInputTotalPrice();
    }

    private void setInputTotalPrice(){
        String medQuantity = inputMedQuantity.getText();
        if (!medQuantity.isEmpty()){
            if (IsValidFields.isValidFields(new ValidNumber<>(Integer.parseInt(medQuantity)))){
                int quantity =  Integer.parseInt(medQuantity);
                int priceUnit = (int)currSelectedMedRow.getFirst().getPrice();
                int totalPrice = quantity * priceUnit;
                inputTotalPrice.setText(Integer.toString(totalPrice));
            }
        }

    }
    @FXML
    public void handleQuantityKeyTyped(KeyEvent event){
        setInputTotalPrice();
    }

    public void updateCurrSelectedMedRow(Medicament selectedRow) {
        int medId = selectedRow.getId();
        String medName = selectedRow.getName();
        String medDesc = selectedRow.getDesc();
        float medPrice =  selectedRow.getPrice();
        int medQuantity = (int)selectedRow.getQuantity();
        int stockId =  selectedRow.getStockId();
        Date medExpDate = selectedRow.getExpDate();
        currSelectedMedRow.clear();
        currSelectedMedRow.add(new Medicament(medId, medName, medDesc,medPrice, medQuantity,stockId, medExpDate));
    }

    public void handleMedRowSelected(Medicament newSelection){
//        btnPurchase.setDisable(false);
        inputPatientId.setDisable(false);
        inputMedQuantity.setDisable(false);
        updateCurrSelectedMedRow(newSelection);
        setFieldsValue(newSelection);
        updateButtonState();
    }

    public void updateButtonState(){
        boolean btnPurchaseDisabled = true;
        String patientId = inputPatientId.getText();
        if (!patientId.isEmpty() ){
            if (IsValidFields.isValidFields(new ValidNumber<>(Integer.parseInt(patientId)))){
                btnPurchaseDisabled = false;
            }
        }
        btnPurchase.setDisable(btnPurchaseDisabled);
    }

}
