package com.nathan.pharmacy.controllers.delivery;

import com.nathan.pharmacy.controllers.supplier.SupplierModelController;
import com.nathan.pharmacy.models.Delivery;
import com.nathan.pharmacy.models.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.security.Key;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeliveryViewController implements Initializable {

    @FXML
    private Button btnCancelDelivery;

    @FXML
    private Button btnDeliver;

    @FXML
    private TableColumn<Delivery, LocalDate> colDelDate;

    @FXML
    private TableColumn<Delivery, Float> colDelPrice;

    @FXML
    private TableColumn<Delivery, Integer> colDelId;

    @FXML
    private TableColumn<Delivery, Integer> colDelQuantity;

    @FXML
    private TableColumn<Delivery, Integer> colMedId;

    @FXML
    private TableColumn<Delivery, String> colMedName;

    @FXML
    private TableColumn<Delivery, Integer> colSupId;

    @FXML
    private TableColumn<Delivery, String> colSupName;

    @FXML
    private TextField inputDelDate;

    @FXML
    private TextField inputDelPrice;

    @FXML
    private TextField inputDelQuantity;

    @FXML
    private TextField inputMedName;

    @FXML
    private TextField inputSupId;

    @FXML
    private ChoiceBox<String> selectMedFilter;

    @FXML
    private TableView<Delivery> tableDelivery;

    private List<Delivery> currSelectedDeliveryRow = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableView();
        try {
            loadTableContent();
            tableDelivery.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
                if (newSelection != null){
                    handleDelRowSelected(newSelection);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleInputDelIdKeyTyped(KeyEvent event) {

    }

    @FXML
    void handleInputMedQuantityKeyTyped(KeyEvent event){

    }

    @FXML
    void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) clearAllField();
        updateButtonState();
    }
    @FXML
    void handleInputMedNameKeyTyped(KeyEvent event){

    }

    public void initTableView(){
        try{
            colSupId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("supId"));
            colSupName.setCellValueFactory(new PropertyValueFactory<Delivery, String>("supName"));
            colMedId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("medId"));
            colMedName.setCellValueFactory(new PropertyValueFactory<Delivery, String>("medName"));
            colDelId.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
            colDelDate.setCellValueFactory(new PropertyValueFactory<Delivery, LocalDate>("date"));
            colDelPrice.setCellValueFactory(new PropertyValueFactory<Delivery, Float>("price"));
            colDelQuantity.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("quantity"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void loadTableContent() throws Exception {
        ObservableList<Delivery> delivery = FXCollections.observableArrayList();
        DeliveryModelController dc = new DeliveryModelController();

        ResultSet rs = dc.selectJoin();

        while (rs.next()){
            int delId = rs.getInt("delId");
            float delPrice = rs.getFloat("delPrice");
            LocalDate delDate = rs.getDate("delDate").toLocalDate();
            int supId = rs.getInt("supId");
            int medId = rs.getInt("medId");
            int delQuantity = rs.getInt("delQuantity");
            String medName = rs.getString("medName");
            String supName = rs.getString("supName");

            delivery.add(new Delivery(delId, delDate, delPrice, supId, medId,delQuantity, medName, supName));
        }
        tableDelivery.setItems(delivery);
    }

    public void setFieldsValue(Delivery newSelection){

    }

    public void updateCurrSelectedDelRow(Delivery selectedRow) {
        int delId = selectedRow.getId();
        float delPrice = selectedRow.getPrice();
        LocalDate delDate = selectedRow.getDate();
        int supId = selectedRow.getSupId();
        int medId = selectedRow.getMedId();
        int delQuantity = selectedRow.getQuantity();
        String medName = selectedRow.getMedName();
        String supName = selectedRow.getSupName();
        currSelectedDeliveryRow.clear();
        currSelectedDeliveryRow.add(new Delivery(delId, delDate, delPrice, supId, medId, delQuantity, medName, supName));
    }

    public void handleDelRowSelected(Delivery newSelection){
        updateCurrSelectedDelRow(newSelection);
        setFieldsValue(newSelection);
        updateButtonState();
    }

    public  void updateButtonState(){

    }

    public void clearAllField(){

    }
}
