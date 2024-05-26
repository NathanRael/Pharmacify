package com.nathan.pharmacy.controllers.purchase;


import com.nathan.pharmacy.controllers.form.ValidName;
import com.nathan.pharmacy.controllers.form.ValidNumber;
import com.nathan.pharmacy.controllers.form.ValidText;
import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.controllers.patient.PatientModelController;
import com.nathan.pharmacy.contstants.AcceptedNumber;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.Purchase;
import com.nathan.pharmacy.utils.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.security.Key;
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

    private final List<Medicament> currSelectedMedRow = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(LocalDate.now());
        btnPurchase.setOnAction(event -> purchaseMedicament());
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

    @FXML
    public void handleQuantityKeyTyped(KeyEvent event){
        setInputTotalPrice();
        setInputMedQuantity();
        updateButtonState();
    }
    @FXML void handleInputPatientKeyTyped(KeyEvent event){
        if (ValidationUtil.validTextField(inputPatientId, new ValidNumber<>(AcceptedNumber.INTEGER))){
            setInputPatientName(Integer.parseInt(inputPatientId.getText()));
        }else{
            inputPatientName.setText("-1");
        }
        updateButtonState();
    }

    @FXML void handleKeyPressed(KeyEvent event){
        if (event.getCode() == KeyCode.ESCAPE) clearAllField();
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
            LocalDate medExpDate = rs.getDate("medExpDate").toLocalDate();
            medicament.add(new Medicament(medId, medName, medDesc,medPrice, medQuantity,stockId, medExpDate));
        }

        tableMedicament.setItems(medicament);
    }


    public void purchaseMedicament(){
        LocalDate purchaseDate = LocalDate.now();
        int medId = currSelectedMedRow.getFirst().getId();
        int patientId = Integer.parseInt(inputPatientId.getText());
        float totalPrice = Float.parseFloat(inputTotalPrice.getText());

        try{
            PurchaseModelController pc = new PurchaseModelController();
            Purchase purchase = new Purchase( purchaseDate,medId, patientId, totalPrice);
            pc.insert(purchase);
            System.out.println("Medicament purchased");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void setFieldsValue(Medicament currentSelection){
        String medName = currentSelection.getName();
        inputMedName.setText(medName);
        setInputTotalPrice();
        setInputMedQuantity();

    }

    private void setInputMedQuantity(){
        if (inputMedQuantity.getText().isEmpty())
            inputMedQuantity.setText("0");
    }

    private void setInputTotalPrice(){
        if (ValidationUtil.validTextField(inputMedQuantity, new ValidNumber<>(AcceptedNumber.INTEGER))){
            String medQuantity = inputMedQuantity.getText();
            int quantity =  Integer.parseInt(medQuantity);
            int priceUnit = (int)currSelectedMedRow.getFirst().getPrice();
            int totalPrice = quantity * priceUnit;
            inputTotalPrice.setText(Integer.toString(totalPrice));
        }else{
            inputTotalPrice.setText("0");
        }

    }

    private void setInputPatientName(int id){
        try{
            PatientModelController mc = new PatientModelController();
            ResultSet rs = mc.selectBy("patientId", Integer.toString(id));
            if (rs.next())
                inputPatientName.setText(rs.getString("patientFName"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateCurrSelectedMedRow(Medicament selectedRow) {
        int medId = selectedRow.getId();
        String medName = selectedRow.getName();
        String medDesc = selectedRow.getDesc();
        float medPrice =  selectedRow.getPrice();
        int medQuantity = (int)selectedRow.getQuantity();
        int stockId =  selectedRow.getStockId();
        LocalDate medExpDate = selectedRow.getExpDate();
        currSelectedMedRow.clear();
        currSelectedMedRow.add(new Medicament(medId, medName, medDesc,medPrice, medQuantity,stockId, medExpDate));
    }

    public void handleMedRowSelected(Medicament newSelection){
        inputPatientId.setDisable(false);
        inputMedQuantity.setDisable(false);
        updateCurrSelectedMedRow(newSelection);
        setFieldsValue(newSelection);
        updateButtonState();
    }

    public void updateButtonState() {
        boolean canPurchase = currSelectedMedRow.getFirst().getQuantity() > Integer.parseInt(inputMedQuantity.getText());
        boolean AllFieldValid = ValidationUtil.validTextField(inputPatientId, new ValidNumber<>(AcceptedNumber.INTEGER)) && ValidationUtil.validTextField(inputMedQuantity, new ValidNumber<>(AcceptedNumber.INTEGER)) && ValidationUtil.validTextField(inputPatientName, new ValidName()) && ValidationUtil.validTextField(inputMedName, new ValidText()) && ValidationUtil.validTextField(inputTotalPrice, new ValidNumber<>(AcceptedNumber.FLOAT));
        btnPurchase.setDisable(!AllFieldValid || !canPurchase);
    }

    public void clearAllField(){
        inputPatientName.clear();
        inputTotalPrice.clear();
        inputPatientId.clear();
        inputMedQuantity.setText("0");
        inputMedName.clear();

    }

}