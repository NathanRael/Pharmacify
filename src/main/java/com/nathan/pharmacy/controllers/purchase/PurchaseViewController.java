package com.nathan.pharmacy.controllers.purchase;


import com.nathan.pharmacy.controllers.form.ValidFloat;
import com.nathan.pharmacy.controllers.form.ValidNumber;
import com.nathan.pharmacy.controllers.form.ValidText;
import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.controllers.patient.PatientModelController;
import com.nathan.pharmacy.contstants.AlertType;
import com.nathan.pharmacy.contstants.DatePattern;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.Purchase;
import com.nathan.pharmacy.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PurchaseViewController implements Initializable {

    @FXML
    private Button btnPurchase;

    @FXML
    private Button btnInvoice;

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

    //Fo the purchase tableView
    @FXML
    private TableColumn<Purchase, Integer> colMedId;

    @FXML
    private TableColumn<Purchase, String> colMedName;

    @FXML
    private TableColumn<Purchase, Integer> colPatientId;

    @FXML
    private TableColumn<Purchase, Integer> colPurchaseQuantity;

    @FXML
    private TableColumn<Purchase, String> colPatientName;

    @FXML
    private TableColumn<Purchase, LocalDateTime> colPurchaseDate;

    @FXML
    private TableColumn<Purchase, Integer> colPurchaseId;

    @FXML
    private TableView<Purchase> tablePurchase;
    @FXML
    private TableColumn<Purchase, Float> colTotalPrice;

    @FXML
    private TextField inputTotalPrice;

    @FXML
    private TextField inputMedName;

    @FXML
    private TextField inputMedQuantity;

    @FXML
    private ChoiceBox<String> selectPatName;

    @FXML
    private TextField inputSearch;

    @FXML
    private ChoiceBox<String> selectMedFilter;

    @FXML
    private TableView<Medicament> tableMedicament;
    @FXML
    private Label txtMostPurchasedMedicamentName;

    @FXML
    private Label txtMostPurchasedMedicamentPrice;

    @FXML
    private Label txtMostPurchasedMedicamentQuantity;

    private final List<Medicament> currSelectedMedRow = new ArrayList<>();
    private final List<Purchase> currSelectedPurchaseRow = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnPurchase.setOnAction(event -> purchaseMedicament());
        btnInvoice.setOnAction(event -> generateInvoice(currSelectedPurchaseRow.get(0).getPatientId(), LocalDateTime.parse(currSelectedPurchaseRow.get(0).getDate())));


        initTableView();
        initPurchaseTableView();
        listenTextFieldEvent();
        initSelectPatientName();
        initMostPurchasedMedicament();


        selectMedFilter.getItems().addAll("Id", "Nom", "Prix");
        selectMedFilter.getSelectionModel().select(1);

        try {
            loadTableContent();
            loadPurchaseTableContent();
            tableMedicament.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
                if (newSelection != null){
                   handleMedRowSelected(newSelection);
                }
            });
            tablePurchase.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
                if (newSelection != null){
                    currSelectedPurchaseRow.clear();
                    currSelectedPurchaseRow.add(new Purchase(newSelection.getDate().toString(), newSelection.getPatientId()));
                   updateButtonState();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMostPurchasedMedicament() {
        try{
            PurchaseModelController pc = new PurchaseModelController();
            ResultSet rs = pc.selectMostPurchasedProduct(1, LocalDate.now().toString());

            if (rs.next()){
                txtMostPurchasedMedicamentName.setText(rs.getString("medName"));
                txtMostPurchasedMedicamentPrice.setText(rs.getString("medPrice")  +  " Ar");
                txtMostPurchasedMedicamentQuantity.setText(String.valueOf(rs.getInt("medQuantity")) + " En stock");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void generateInvoice(int patientId, LocalDateTime purchaseDate) {
        PdfManager pdfManager = new PdfManager();
        pdfManager.print(patientId, purchaseDate);
        HistoryUtil.pushHistory(Session.getInstance().getUserName(), "Generation d'une facture du patient : " + currSelectedPurchaseRow.get(0).getPatName());
        System.out.println("Invoice created");
        AlertUtils.showAlert("Facture generée", AlertType.SUCCESS);
    }

    @FXML
    public void handleQuantityKeyTyped(KeyEvent event){
        setInputTotalPrice();
        setInputMedQuantity();
        updateButtonState();
    }
    private void listenTextFieldEvent() {
        inputSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                try {
                    loadTableContent();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                searchMedicament();
            }
        });

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

    public void initPurchaseTableView(){
        try{
            colPurchaseId.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("id"));
            colTotalPrice.setCellValueFactory(new PropertyValueFactory<Purchase, Float>("totalPrice"));
            colPurchaseQuantity.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("quantity"));
            colPurchaseDate.setCellValueFactory(new PropertyValueFactory<Purchase, LocalDateTime>("date"));
            colMedId.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("medId"));
            colMedName.setCellValueFactory(new PropertyValueFactory<Purchase, String>("medName"));
            colPatientId.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("patientId"));
            colPatientName.setCellValueFactory(new PropertyValueFactory<Purchase, String>("patName"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void loadTableContent() throws Exception {
        ObservableList<Medicament> medicament = FXCollections.observableArrayList();
        MedicamentModelController mc = new MedicamentModelController();

        ResultSet rs = mc.selectAllDisponibleMed();

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
        initMostPurchasedMedicament();
    }

    public void loadPurchaseTableContent() throws Exception {
        ObservableList<Purchase> purchase = FXCollections.observableArrayList();
        PurchaseModelController pc = new PurchaseModelController();

        ResultSet rs = pc.selectJoin();

        while (rs.next()){
            int purchaseId = rs.getInt("purchaseId");
            int medId = rs.getInt("medId");
            int patientId = rs.getInt("patientId");
            String purchaseDate = rs.getDate("purchaseDate").toLocalDate().atTime(rs.getTime("purchaseDate").toLocalTime()).toString();
            float totalPrice = rs.getFloat("totalPrice");
            String medName = rs.getString("medName");
            String patientName = rs.getString("patientFName");
            int purchaseQuantity = rs.getInt("purchaseQuantity");
            purchase.add(new Purchase(purchaseId, purchaseDate.toString(),purchaseQuantity,medId, patientId, totalPrice, medName, patientName ));
        }

        tablePurchase.setItems(purchase);
    }


    public void purchaseMedicament(){
        LocalDateTime purchaseDate = LocalDateTime.now();
        int medId = currSelectedMedRow.getFirst().getId();
        String patientFName = selectPatName.getSelectionModel().getSelectedItem();
        int patientId = getPatientId(patientFName);
        float totalPrice = Float.parseFloat(inputTotalPrice.getText());
        int quantity = Integer.parseInt(inputMedQuantity.getText());

        try{
            PurchaseModelController pc = new PurchaseModelController();
            MedicamentModelController mc = new MedicamentModelController();

            ResultSet rs = mc.selectBy("medId", String.valueOf(medId));
            rs.next();
            int oldQuantity = rs.getInt("medQuantity");
            int newQuantity = oldQuantity - quantity;
            if (newQuantity >= 0){
                Purchase purchase = new Purchase( purchaseDate.toString(), quantity, medId, patientId, totalPrice);
                pc.insert(purchase);
                mc.updateBy("medQuantity", newQuantity, "medId", medId);
                System.out.println("Medicament purchased");
                AlertUtils.showAlert("Medicament vendu", AlertType.SUCCESS);

                HistoryUtil.pushHistory(Session.getInstance().getUserName(), "Vente de medicament (" + inputMedName.getText() + ") à " + patientFName);
                loadTableContent();
                loadPurchaseTableContent();
                clearAllField();
            }else{
                System.out.println("The quantity is not enough");
                AlertUtils.showAlert("La quantité est insuffisant", AlertType.ERROR);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void searchMedicament() {
        String search = inputSearch.getText();
        String filterMode = selectMedFilter.getSelectionModel().getSelectedItem();

        try {
            ObservableList<Medicament> medicaments = FXCollections.observableArrayList();
            MedicamentModelController mc = new MedicamentModelController();
            ResultSet rs = null;
            switch (filterMode) {
                case "Id" -> rs = mc.searchLike("medId", search);
                case "Nom" -> rs = mc.searchLike("medName", search);
                case "Prix" -> rs = mc.searchLike("medPrice", search);
                default -> rs = mc.searchLike("medId", search);
            }

            while(rs.next()){
                int medId = rs.getInt("medId");
                String medName = rs.getString("medName");
                String medDesc = rs.getString("medDesc");
                float medPrice = rs.getFloat("medPrice");
                int medQuantity = rs.getInt("medQuantity");
                int stockId = rs.getInt("stockId");
                LocalDate medExpDate = rs.getDate("medExpDate").toLocalDate();
                medicaments.add(new Medicament(medId, medName, medDesc, medPrice, medQuantity, stockId, medExpDate));
            }
            tableMedicament.setItems(medicaments);
        } catch (Exception ex) {
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
/*        if (inputMedQuantity.getText().isEmpty())
            inputMedQuantity.setText("0");*/
    }

    private void initSelectPatientName(){
        try{
            PatientModelController pc = new PatientModelController();
            ResultSet rs = pc.selectAll();

            while (rs.next()){
                selectPatName.getItems().add(rs.getString("patientFName"));
            }
            selectPatName.getSelectionModel().select(0);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private int getPatientId(String patientFName){
        try{
            PatientModelController pc = new PatientModelController();
            ResultSet rs = pc.selectBy("patientFName", patientFName);
            if (rs.next()){
                return rs.getInt("patientId");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }

    private void setInputTotalPrice(){
        if (ValidationUtil.validTextField(inputMedQuantity, new ValidNumber())){
            String medQuantity = inputMedQuantity.getText();
            int quantity =  Integer.parseInt(medQuantity);
            int priceUnit = (int)currSelectedMedRow.getFirst().getPrice();
            int totalPrice = quantity * priceUnit;
            inputTotalPrice.setText(Integer.toString(totalPrice));
        }else{
            inputTotalPrice.setText("0");
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
        selectPatName.setDisable(false);
        inputMedQuantity.setDisable(false);
        updateCurrSelectedMedRow(newSelection);
        setFieldsValue(newSelection);
        updateButtonState();
    }

    public void updateButtonState() {
        boolean canPurchase = false;
        if (!currSelectedMedRow.isEmpty() && !inputMedQuantity.getText().isEmpty())
            canPurchase = currSelectedMedRow.get(0).getQuantity() >= Integer.parseInt(inputMedQuantity.getText()) && currSelectedMedRow.get(0).getQuantity() > 0 && currSelectedMedRow.get(0).getExpDate().isAfter(LocalDate.now());

        boolean AllFieldValid = !selectPatName.getSelectionModel().getSelectedItem().isEmpty() && ValidationUtil.validTextField(inputMedQuantity, new ValidNumber())  && ValidationUtil.validTextField(inputMedName, new ValidText()) && ValidationUtil.validTextField(inputTotalPrice, new ValidFloat());
        btnPurchase.setDisable(!AllFieldValid || !canPurchase);
        btnInvoice.setDisable(currSelectedPurchaseRow.isEmpty());
    }



    public void clearAllField(){
        inputTotalPrice.clear();
        selectPatName.getSelectionModel().select(0);
        inputMedQuantity.setText("0");
        inputMedName.clear();
        updateButtonState();
    }

}
