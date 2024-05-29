package com.nathan.pharmacy.controllers.delivery;

import com.nathan.pharmacy.controllers.form.ValidNumber;
import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.contstants.AcceptedNumber;
import com.nathan.pharmacy.interfaces.FieldValidator;
import com.nathan.pharmacy.models.Delivery;
import com.nathan.pharmacy.utils.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
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
    private DatePicker inputDelDate;

    @FXML
    private TextField inputDelPrice;

    @FXML
    private TextField inputDelQuantity;

    @FXML
    private ChoiceBox<String> selectMedName;

    @FXML
    private TextField inputSupId;

    @FXML
    private ChoiceBox<String> selectMedFilter;

    @FXML
    private TableView<Delivery> tableDelivery;

    private List<Delivery> currSelectedDeliveryRow = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputDelDate.setEditable(false);
        inputDelDate.setValue(LocalDate.now());
        btnDeliver.setOnAction(event -> deliver());
        btnCancelDelivery.setOnAction(event -> cancelDelivery(currSelectedDeliveryRow.getFirst().getId(), currSelectedDeliveryRow.getFirst().getDate()));

        listenKeyEvent();
        initTableView();
        initSelectMedName();


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
    void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) clearAllField();
        updateButtonState();
    }

    public void listenKeyEvent(){
        EventHandler<Event> keyTypeHandler = event -> updateButtonState();

        inputSupId.setOnKeyTyped(keyTypeHandler);
        inputDelPrice.setOnKeyTyped(keyTypeHandler);
        inputDelDate.setOnKeyTyped(keyTypeHandler);
        inputDelQuantity.setOnKeyTyped(keyTypeHandler);
        selectMedName.setOnContextMenuRequested(keyTypeHandler);
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
    public void deliver(){
        int supId = Integer.parseInt(inputSupId.getText());
        String medName = selectMedName.getSelectionModel().getSelectedItem();
        int delQuantity = Integer.parseInt(inputDelQuantity.getText());
        float delPrice = Float.parseFloat(inputDelPrice.getText());

        LocalDate delDate = inputDelDate.getValue();

        int medId = getMedId(medName);

        DeliveryModelController dc = new DeliveryModelController();
        Delivery delivery = new Delivery(delDate,delPrice,supId, medId, delQuantity);
        try {
            dc.insert(delivery);
            updateMedQuantity(delQuantity, medId);
            System.out.println("Delivery added");
            loadTableContent();
            clearAllField();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateMedQuantity(int medQuantity, int medId){
        int currentQuantity, newQuantity;
        try {
            MedicamentModelController mc = new MedicamentModelController();
            ResultSet rs = mc.selectBy("medId", String.valueOf(medId));

            if (rs.next()){
                currentQuantity = rs.getInt("medQuantity");
                newQuantity = currentQuantity + medQuantity;
                mc.updateBy("medQuantity", newQuantity, "medId", medId);
                System.out.println("Medicament updated");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public int getMedId(String medName){
        try {
            MedicamentModelController mc = new MedicamentModelController();
            ResultSet rs = mc.selectBy("medName",medName);
            if (rs.next())
                return rs.getInt("medId");
            return -1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  -1;
    }

    public void initSelectMedName(){
        try {
            MedicamentModelController mc = new MedicamentModelController();
            ResultSet rs = mc.selectAll();
            while (rs.next())
                selectMedName.getItems().add(rs.getString("medName"));
            selectMedName.getSelectionModel().select(0);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void cancelDelivery(int delId, LocalDate delDate){
        LocalDate currDate = LocalDate.now();
        if (delDate.isAfter(currDate)){
            DeliveryModelController dc = new DeliveryModelController();
            try {
                dc.delete(delId);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
            System.out.println("Cannot be canceled");
        }
    }

    public void setFieldsValue(Delivery newSelection){
        inputSupId.setText(Integer.toString(newSelection.getSupId()));
        selectMedName.getSelectionModel().select(newSelection.getMedName());
        inputDelQuantity.setText(String.valueOf(newSelection.getQuantity()));
        inputDelPrice.setText(String.valueOf(newSelection.getPrice()));
        inputDelDate.setValue(newSelection.getDate());
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

    public void updateButtonState() {
        boolean isValidSupId = validText(inputSupId, new ValidNumber());
        boolean isValidDelQuantity = validText(inputDelQuantity, new ValidNumber());
        boolean isValidDelPrice = validText(inputDelPrice, new ValidNumber());
        boolean isMedNameSelected = selectMedName.getSelectionModel().getSelectedItem() != null;
        boolean isDateValid = inputDelDate.getValue() != null;

        boolean canDeliver = isValidSupId && isValidDelQuantity && isValidDelPrice && isMedNameSelected && isDateValid;

        boolean canCancelDelivery = inputDelDate.getValue() != null && inputDelDate.getValue().isAfter(LocalDate.now()) && !currSelectedDeliveryRow.isEmpty();

        btnDeliver.setDisable(!canDeliver);
        btnCancelDelivery.setDisable(!canCancelDelivery);
    }


    public void clearAllField(){
        inputDelDate.setValue(LocalDate.now());
        inputDelPrice.clear();
        inputDelQuantity.setText("1");
        inputSupId.clear();
        // Selecting the most used medicament
        selectMedName.getSelectionModel().select(0);
        updateButtonState();
    }

    public void selectMostUsedMedicament(){
        //TODO
    }

    public boolean validText(TextField textField, FieldValidator fieldValidator){
        return ValidationUtil.validTextField(textField, fieldValidator);
    }


}
