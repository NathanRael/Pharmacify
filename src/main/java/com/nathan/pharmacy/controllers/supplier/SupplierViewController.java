package com.nathan.pharmacy.controllers.supplier;

import com.nathan.pharmacy.controllers.form.ValidName;
import com.nathan.pharmacy.controllers.form.ValidNumber;
import com.nathan.pharmacy.controllers.form.ValidPhone;
import com.nathan.pharmacy.controllers.form.ValidText;
import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.controllers.purchase.PurchaseModelController;
import com.nathan.pharmacy.contstants.AcceptedNumber;
import com.nathan.pharmacy.interfaces.FieldValidator;
import com.nathan.pharmacy.models.Delivery;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.Purchase;
import com.nathan.pharmacy.models.Supplier;
import com.nathan.pharmacy.utils.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.w3c.dom.Text;

import javax.xml.validation.Validator;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierViewController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private TableColumn<Supplier, Integer> colSupId;

    @FXML
    private TableColumn<Supplier, String> colSupName;

    @FXML
    private TableColumn<Supplier, String> colSupPhone;

    @FXML
    private TextField inputSupId;

    @FXML
    private TextField inputSupName;

    @FXML
    private TextField inputSupPhone;

    @FXML
    private TextField inputSupSearch;

    @FXML
    private ChoiceBox<String> selectSupFilter;

    @FXML
    private TableView<Supplier> tableSupplier;


    private List<Supplier> currSelectedSupplierRow = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setOnAction(event -> addSupplier());
        btnEdit.setOnAction(event -> updateSupplier());
        btnDelete.setOnAction(event -> deleteSupplier(currSelectedSupplierRow.getFirst().getId()));

        selectSupFilter.getItems().addAll("Id", "Nom", "Telephone");
        selectSupFilter.getSelectionModel().select(0);

        initTableView();
        listenToEvent();

        try {
            loadTableContent();
            tableSupplier.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
                if (newSelection != null){
                    handleSupRowSelected(newSelection);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleInputSupNameTyped(KeyEvent event) {
        updateButtonState();
    }
    @FXML
    void handleInputSupPhoneTyped(KeyEvent event) {
        updateButtonState();
    }

    @FXML
    void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) clearAllField();
        updateButtonState();
    }

    private void listenToEvent(){
        inputSupSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                try {
                    loadTableContent();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                searchSupplier();
            }
        });
    }

    public void addSupplier(){
        String supName = inputSupName.getText();
        String supPhone = inputSupPhone.getText();

        try{
            SupplierModelController sc = new SupplierModelController();
            Supplier supplier = new Supplier(supName, supPhone);
            sc.insert(supplier);
            System.out.println("Supplier added");
            loadTableContent();
            clearAllField();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deleteSupplier(int supId){
        try{
            SupplierModelController sc = new SupplierModelController();
            sc.delete(supId);
            System.out.println("Supplier deleted");
            loadTableContent();
            clearAllField();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateSupplier(){
        int supId = Integer.parseInt(inputSupId.getText());
        String supName = inputSupName.getText();
        String supPhone = inputSupPhone.getText();

        try{
            SupplierModelController sc = new SupplierModelController();
            Supplier supplier = new Supplier(supId,supName, supPhone);
            sc.update(supplier);
            System.out.println("Supplier updated");
            loadTableContent();
            clearAllField();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void searchSupplier() {
        String search = inputSupSearch.getText();
        String filterMode = selectSupFilter.getSelectionModel().getSelectedItem();

        try {
            ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
            SupplierModelController sc = new SupplierModelController();
            ResultSet rs = null;
            switch (filterMode) {
                case "Id" -> rs = sc.searchLike("supId", search);
                case "Nom" -> rs = sc.searchLike("supName", search);
                case "Telephone" -> rs = sc.searchLike("supPhone", search);
                default -> rs = sc.searchLike("supId", search);
            }

            while (rs.next()){
                int supId = rs.getInt("supId");
                String supName = rs.getString("supName");
                String supPhone = rs.getString("supPhone");
                suppliers.add(new Supplier(supId, supName, supPhone));
            }
            tableSupplier.setItems(suppliers);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void updateCurrSelectedMedRow(Supplier selectedRow) {
        int supplierId = selectedRow.getId();
        String supplierName = selectedRow.getName();
        String supplierPhone = selectedRow.getPhone();
        currSelectedSupplierRow.clear();
        currSelectedSupplierRow.add(new Supplier(supplierId, supplierName, supplierPhone));
    }

    public void loadTableContent() throws Exception {
        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        SupplierModelController sc = new SupplierModelController();

        ResultSet rs = sc.selectAll();

        while (rs.next()){
            int supId = rs.getInt("supId");
            String supName = rs.getString("supName");
            String supPhone = rs.getString("supPhone");
            suppliers.add(new Supplier(supId, supName, supPhone));
        }
        tableSupplier.setItems(suppliers);
    }

    public void initTableView(){
        try{
            colSupId.setCellValueFactory(new PropertyValueFactory<Supplier, Integer>("id"));
            colSupName.setCellValueFactory(new PropertyValueFactory<Supplier, String>("name"));
            colSupPhone.setCellValueFactory(new PropertyValueFactory<Supplier, String>("phone"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void setFieldsValue(Supplier newSelection){
        inputSupId.setText(Integer.toString(newSelection.getId()));
        inputSupName.setText(newSelection.getName());
        inputSupPhone.setText(newSelection.getPhone());
    }

    public void handleSupRowSelected(Supplier newSelection){
        inputSupPhone.setDisable(false);
        inputSupName.setDisable(false);

        updateCurrSelectedMedRow(newSelection);
        setFieldsValue(newSelection);
        updateButtonState();
    }

    public void updateButtonState(){
        boolean canAdd = validText(inputSupName, new ValidName()) && validText(inputSupPhone, new ValidPhone());
        boolean canEdit = validText(inputSupName, new ValidName()) && validText(inputSupPhone, new ValidPhone());
        boolean canDelete = validText(inputSupId, new ValidNumber());

        btnAdd.setDisable(!canAdd);
        btnEdit.setDisable(!canEdit || !canDelete);
        btnDelete.setDisable(!canDelete);
    }


    public void clearAllField(){;
        inputSupName.clear();
        inputSupPhone.clear();
        inputSupId.clear();
        updateButtonState();
    }

    public boolean validText(TextField textField, FieldValidator fieldValidator){
        return ValidationUtil.validTextField(textField, fieldValidator);
    }


}
