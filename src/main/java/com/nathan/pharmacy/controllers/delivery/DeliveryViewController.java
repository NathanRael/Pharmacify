package com.nathan.pharmacy.controllers.delivery;

import com.nathan.pharmacy.controllers.form.ValidFloat;
import com.nathan.pharmacy.controllers.form.ValidNumber;
import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.controllers.supplier.SupplierModelController;
import com.nathan.pharmacy.interfaces.FieldValidator;
import com.nathan.pharmacy.models.Delivery;
import com.nathan.pharmacy.utils.MedicamentUtil;
import com.nathan.pharmacy.utils.PurchaseUtil;
import com.nathan.pharmacy.utils.SupplierUtil;
import com.nathan.pharmacy.utils.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

import java.net.URL;
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
    private TableColumn<Delivery, LocalDate> colExpDate;

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
    private DatePicker inputExpDate;

    @FXML
    private TextField inputDelPrice;

    @FXML
    private TextField inputDelQuantity;

    @FXML
    private ChoiceBox<String> selectMedName;

    @FXML
    private ChoiceBox<String> selectSupName;

    @FXML
    private ChoiceBox<String> selectMedFilter;
    @FXML
    private TextField inputMedSearch;

    @FXML
    private TableView<Delivery> tableDelivery;

    private List<Delivery> currSelectedDeliveryRow = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputExpDate.setEditable(false);
        inputExpDate.setValue(LocalDate.of(2026,10,24));
        btnDeliver.setOnAction(event -> deliver());
        btnCancelDelivery.setOnAction(event -> cancelDelivery(currSelectedDeliveryRow.getFirst().getId(), currSelectedDeliveryRow.getFirst().getDate()));

        listenKeyEvent();
        initTableView();
        initSelectMedName();
        initSelectSupName();

        selectMedFilter.getItems().addAll("Id", "Prix", "Quantité");
        selectMedFilter.getSelectionModel().select(0);

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

    private void listenKeyEvent(){
        EventHandler<Event> keyTypeHandler = event -> updateButtonState();

//        inputSupId.setOnKeyTyped(keyTypeHandler);
        inputDelPrice.setOnKeyTyped(keyTypeHandler);
        inputDelQuantity.setOnKeyTyped(keyTypeHandler);
        selectMedName.setOnContextMenuRequested(keyTypeHandler);

        inputExpDate.setDayCellFactory(picker -> new DateCell(){
            @Override
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())){
                    setDisable(true);
                    setStyle("-fx-background-color : rgba(226, 226, 226, 0.1)");
                }
                if (!empty){
                    setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY){
                            updateButtonState();
                        }
                    });
                }
            }
        });

        inputMedSearch.textProperty().addListener((observable, oldValue, newValue) -> {
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
            colExpDate.setCellValueFactory(new PropertyValueFactory<Delivery, LocalDate>("medExpDate"));
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
            LocalDate medExpDate = rs.getDate("medExpDate").toLocalDate();
            int supId = rs.getInt("supId");
            int medId = rs.getInt("medId");
            int delQuantity = rs.getInt("delQuantity");
            String medName = rs.getString("medName");
            String supName = rs.getString("supName");

            delivery.add(new Delivery(delId, delDate, delPrice, supId, medId,delQuantity, medName, supName, medExpDate));
        }
        tableDelivery.setItems(delivery);
    }
    public void deliver(){
        int supId = 0;
        try {
            supId = SupplierUtil.getSupId(selectSupName.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String medName = selectMedName.getSelectionModel().getSelectedItem();
        int delQuantity = Integer.parseInt(inputDelQuantity.getText());
        float delPrice = Float.parseFloat(inputDelPrice.getText());
        LocalDate medExpDate = inputExpDate.getValue();

        LocalDate delDate = LocalDate.now();

        int medId = getMedId(medName);

        DeliveryModelController dc = new DeliveryModelController();

        Delivery delivery = new Delivery(delDate,delPrice,supId, medId, delQuantity);
        try {
            MedicamentModelController mc = new MedicamentModelController();

            dc.insert(delivery);
            mc.updateBy("medExpDate", medExpDate, "medId", getMedId(selectMedName.getSelectionModel().getSelectedItem()));
            updateMedQuantity(delQuantity, medId);
            System.out.println("Delivery added");
            loadTableContent();
            clearAllField();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateMedQuantity(int delQuantity, int medId){
        int currentQuantity, newQuantity;
        try {
            MedicamentModelController mc = new MedicamentModelController();
            ResultSet rs = mc.selectBy("medId", String.valueOf(medId));

            if (rs.next()){
                currentQuantity = rs.getInt("medQuantity");
                newQuantity = currentQuantity + delQuantity;
                System.out.println("new Q :" + newQuantity);
                mc.updateBy("medQuantity", newQuantity, "medId", medId);
                System.out.println("Medicament updated");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void searchMedicament() {
        String search = inputMedSearch.getText();
        String filterMode = selectMedFilter.getSelectionModel().getSelectedItem();


        try {
            ObservableList<Delivery> deliveries = FXCollections.observableArrayList();
            DeliveryModelController dc = new DeliveryModelController();
            ResultSet rs = null;
            switch (filterMode) {
                case "Id" -> rs = dc.searchLike("delId", search);
                case "Quantité" -> rs = dc.searchLike("delQuantity", search);
                case "Prix" -> rs = dc.searchLike("delPrice", search);
                default -> rs = dc.searchLike("delId", search);
            }

            while(rs.next()){
                int delId = rs.getInt("delId");
                float delPrice = rs.getFloat("delPrice");
                LocalDate delDate = rs.getDate("delDate").toLocalDate();
                LocalDate expDate = rs.getDate("medExpDate").toLocalDate();
                int supId = rs.getInt("supId");
                int medId = rs.getInt("medId");
                int delQuantity = rs.getInt("delQuantity");
                String medName = rs.getString("medName");
                String supName = rs.getString("supName");
                deliveries.add(new Delivery(delId, delDate, delPrice, supId, medId,delQuantity, medName, supName, expDate));
            }
            tableDelivery.setItems(deliveries);
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
//            selectMedName.getSelectionModel().select(0);
            selectMedName.setValue(PurchaseUtil.getMostPurchasedMedicament());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void initSelectSupName() {
        try {
            SupplierModelController sc = new SupplierModelController();
            ResultSet rs = sc.selectAll();
            while (rs.next())
                selectSupName.getItems().add(rs.getString("supName"));
//            selectSupName.getSelectionModel().select(0);
            selectSupName.setValue(SupplierUtil.getFavoriteSupplier());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void cancelDelivery(int delId, LocalDate delDate){
        LocalDate currDate = LocalDate.now();
        int currentQuantity = currSelectedDeliveryRow.get(0).getQuantity();
        int medId = currSelectedDeliveryRow.get(0).getMedId();
        int newQuantity;
        if (delDate.isAfter(currDate)){

            DeliveryModelController dc = new DeliveryModelController();
            try {

                MedicamentModelController mc = new MedicamentModelController();
                ResultSet rs = mc.selectBy("medId", String.valueOf(medId));
                rs.next();

                newQuantity = rs.getInt("medQuantity") - currentQuantity;

                mc.updateBy("medQuantity", newQuantity,"medId",medId );
                dc.delete(delId);
                System.out.println("Purchase canceled");
                loadTableContent();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            clearAllField();
        }else{
            System.out.println("Cannot be canceled");
        }
    }

    public void setFieldsValue(Delivery newSelection){
//        inputSupId.setText(Integer.toString(newSelection.getSupId()));
        selectSupName.setValue(newSelection.getSupName());
        selectMedName.getSelectionModel().select(newSelection.getMedName());
        inputDelQuantity.setText(String.valueOf(newSelection.getQuantity()));
        inputDelPrice.setText(String.valueOf(newSelection.getPrice()));
        inputExpDate.setValue(newSelection.getMedExpDate());
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
        boolean isValidSupId = !selectSupName.getSelectionModel().getSelectedItem().isEmpty();
        boolean isValidDelQuantity = validText(inputDelQuantity, new ValidNumber());
        boolean isValidDelPrice = validText(inputDelPrice, new ValidFloat());
        boolean isMedNameSelected = selectMedName.getSelectionModel().getSelectedItem() != null;
        boolean isDateValid = inputExpDate.getValue() != null;


        boolean canDeliver = isValidSupId && isValidDelQuantity && isValidDelPrice && isMedNameSelected && isDateValid;

        boolean canCancelDelivery = inputExpDate.getValue() != null && inputExpDate.getValue().isAfter(LocalDate.now()) && !currSelectedDeliveryRow.isEmpty();

        btnDeliver.setDisable(!canDeliver);
        btnCancelDelivery.setDisable(!canCancelDelivery);
    }


    public void clearAllField(){
        inputExpDate.setValue(LocalDate.of(2026,12,24));
        inputDelPrice.clear();
        inputDelQuantity.setText("1");
        selectSupName.getSelectionModel().select(0);
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
