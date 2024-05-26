package com.nathan.pharmacy.controllers.medicament;

import com.nathan.pharmacy.controllers.stock.StockModelController;
import com.nathan.pharmacy.controllers.form.ValidLongText;
import com.nathan.pharmacy.controllers.form.ValidNumber;
import com.nathan.pharmacy.controllers.form.ValidText;
import com.nathan.pharmacy.controllers.stock.StockViewController;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.Singleton;
import com.nathan.pharmacy.models.Stock;
import com.nathan.pharmacy.utils.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MedicamentViewController implements Initializable {

    @FXML
    private Button btnAddMed;

    @FXML
    private Button btnDeleteMed;

    @FXML
    private Button btnEditMed;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Medicament, String> colDesc;

    @FXML
    private TableColumn<Medicament, LocalDate> colExpireDate;

    @FXML
    private TableColumn<Medicament, Integer> colId;

    @FXML
    private TableColumn<Medicament, String> colName;

    @FXML
    private TableColumn<Medicament, Float> colPrice;

    @FXML
    private TableColumn<Medicament, Integer> colQuantity;

    @FXML
    private TableColumn<Medicament, Integer> colStockId;

    @FXML
    private TextField inputMedDesc;

    @FXML
    private TextField inputMedName;

    @FXML
    private TextField inputMedPrice;

    @FXML
    private ChoiceBox<String> selectStockId;

    @FXML
    private TextField inputSearch;

    @FXML
    private ChoiceBox<String> selectMedFilter;

    @FXML
    private TableView<Medicament> tableMedicament;

    private int currSelectedId;

    private List<Stock> stockInfo = new ArrayList<>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setStockInfo(stockInfo);

        btnAddMed.setOnAction(event -> addMedicament());
        btnDeleteMed.setOnAction(event -> deleteMedicament(currSelectedId));
        btnEditMed.setOnAction(event -> updateMedicament(currSelectedId));

        selectMedFilter.getItems().addAll("Prix", "Date");
        initSelectStock();
        initTableView();
        try {
            loadTableContent();
            //Listen for selection changes
            tableMedicament.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
                if (newSelection != null){
                    int currentIndex = tableMedicament.getSelectionModel().getSelectedIndex();
                    setFieldsValue(currentIndex, newSelection);
                }
            });

        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }

    public void initSelectStock(){
        for (Stock stock : stockInfo){
            selectStockId.getItems().add(Integer.toString(stock.getId()));
        }
        selectStockId.getSelectionModel().select(0);
    }

    private void setFieldsValue(int row, Medicament currentSelection){
        btnDeleteMed.setDisable(false);
        btnEditMed.setDisable(false);

        currSelectedId = currentSelection.getId();
        String medName = currentSelection.getName();
        String medDesc = currentSelection.getDesc();
        float medPrice =  currentSelection.getPrice();
        int stockId =  currentSelection.getStockId();

        inputMedName.setText(medName);
        inputMedDesc.setText(medDesc);
        inputMedPrice.setText(Float.toString(medPrice));
        selectStockId.setValue(Integer.toString(stockId));

    }
    private void addMedicament(){
        String medName = inputMedName.getText();
        String medDesc = inputMedDesc.getText();
        float medPrice = Float.parseFloat(inputMedPrice.getText());
        int medQuantity =  0;
        int stockId = 1;
        Date medExpDate = new Date(0);

//        boolean allFieldValidated = IsValidFields.isValidFields(new ValidText(medName), new ValidLongText(medDesc), new ValidNumber<>(medPrice));
            boolean allFieldValidated = true;

        Medicament medicament = new Medicament(medName, medDesc, medPrice, medQuantity, stockId, medExpDate);
        MedicamentModelController mc = null;

        if (allFieldValidated){
            try {
                mc = new MedicamentModelController();
                mc.insert(medicament);
                loadTableContent();
                clearAllField();
                setMedTableObserver("added");
                System.out.println("Medicament added");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Invalid field");
        }
    }

    public void deleteMedicament(int id){
        try {
            MedicamentModelController mc = new MedicamentModelController();
            mc.delete(id);
            System.out.println("Medicament deleted successfully");
            loadTableContent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateMedicament(int id){
        try{
            String medName = inputMedName.getText();
            String medDesc = inputMedDesc.getText();
            float medPrice =  Float.parseFloat(inputMedPrice.getText());
            int stockId = Integer.parseInt(selectStockId.getSelectionModel().getSelectedItem());
            MedicamentModelController mc = new MedicamentModelController();
            mc.updateBy( "stockId", stockId ,"medName", medName, "medDesc", medDesc, "medPrice", medPrice, "medId", id);
            loadTableContent();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private  void loadTableContent() throws Exception {
        ObservableList<Medicament> medicaments = FXCollections.observableArrayList();
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
            medicaments.add(new Medicament(medId, medName, medDesc,medPrice, medQuantity,stockId, medExpDate));
        }

        tableMedicament.setItems(medicaments);
    }



    public void initTableView(){
        try{
            colId.setCellValueFactory(new PropertyValueFactory<Medicament, Integer>("id"));
            colStockId.setCellValueFactory(new PropertyValueFactory<Medicament, Integer>("stockId"));
            colName.setCellValueFactory(new PropertyValueFactory<Medicament, String>("name"));
            colPrice.setCellValueFactory(new PropertyValueFactory<Medicament, Float>("price"));
            colDesc.setCellValueFactory(new PropertyValueFactory<Medicament, String>("desc"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<Medicament, Integer>("quantity"));
            colExpireDate.setCellValueFactory(new PropertyValueFactory<Medicament, LocalDate>("expDate"));

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void setStockInfo(List<Stock> stockInfo){
        StockViewController.setStockInfo(stockInfo);
    }

    private void clearAllField(){
        inputMedDesc.clear();
        inputMedName.clear();
        inputMedPrice.clear();
        selectStockId.setValue("1");
    }

    private void setMedTableObserver(String indication){
        Singleton.getInstance().getTableObserver().getMedTableChangedProperty().set(indication);
    }


    @FXML
    void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) clearAllField();
    }
}
