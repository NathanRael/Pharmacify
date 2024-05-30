package com.nathan.pharmacy.controllers.prescription;

import com.nathan.pharmacy.controllers.form.ValidNumber;
import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.controllers.patient.PatientModelController;
import com.nathan.pharmacy.interfaces.FieldValidator;
import com.nathan.pharmacy.models.*;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrescriptionViewController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnUse;

    @FXML
    private TableColumn<Prescription, Integer> colPatId;

    @FXML
    private TableColumn<Prescription, String> colPatName;

    @FXML
    private TableColumn<Prescription, LocalDateTime> colPrescDate;

    @FXML
    private TableColumn<Prescription, String> colPrescDesc;


    @FXML
    private TableColumn<Prescription, LocalDateTime> colPrescDuration;

    @FXML
    private TableColumn<Prescription, Integer> colPrescId;

    @FXML
    private ChoiceBox<String> selectPatientFName;

    @FXML
    private TextField inputPrescDuration;
    @FXML
    private TextArea inputPrescDesc;

    @FXML
    private TextField inputPrescMedNum;

    @FXML
    private VBox medicamentContainer;

    @FXML
    private ChoiceBox<String> selectMedFilter;

    @FXML
    private TableView<Prescription> tablePrescription;

    private List<String> medNameInDb = new ArrayList<>();
    private List<ChoiceBox<String>> selectMedNameList = new ArrayList<>();
    private List<MedUsage> inputMedUsageList = new ArrayList<>();
    private final List<Prescription> currSelectedPrescRow = new ArrayList<>();
    private List<PrescMedInfo> currMedUsageList = new ArrayList<>();

    @FXML
    void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) clearAllField();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setOnAction(event -> addPrescription() );
        btnDelete.setOnAction(event -> deletePrescription(currSelectedPrescRow.get(0).getId()));
        btnUse.setOnAction(event -> usePrecription());

        updateViews();
        initTableView();
        inputPrescMedNum.setText("1");
        generateTextField(1);
        listenKeyEvent();

        try {
            loadTableContent();
            tablePrescription.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
                if (newSelection != null){
                    handlePrescRowSelected(newSelection);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenKeyEvent() {
        EventHandler<Event> keyTypeHandler = event -> updateButtonState();

        inputPrescMedNum.setOnKeyTyped(keyTypeHandler);
        inputPrescDuration.setOnKeyTyped(keyTypeHandler);
        selectPatientFName.setOnContextMenuRequested(keyTypeHandler);

        inputPrescMedNum.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.matches("\\d")) {
                int value = Integer.parseInt(newValue);
                if (value > 0) generateTextField(value);
                for (MedUsage medUsage : inputMedUsageList){
                    medUsage.inputMorningQuantity().setOnKeyTyped(keyTypeHandler);
                    medUsage.inputAfternoonQuantity().setOnKeyTyped(keyTypeHandler);
                    medUsage.inputNoonQuantity().setOnKeyTyped(keyTypeHandler);
                }
                updateButtonState();
            }
        });

        for (MedUsage medUsage : inputMedUsageList){
            medUsage.inputMorningQuantity().setOnKeyTyped(keyTypeHandler);
            medUsage.inputAfternoonQuantity().setOnKeyTyped(keyTypeHandler);
            medUsage.inputNoonQuantity().setOnKeyTyped(keyTypeHandler);
        }
    }


    public void initTableView(){
        try{
            colPrescId.setCellValueFactory(new PropertyValueFactory<Prescription, Integer>("id"));
            colPatName.setCellValueFactory(new PropertyValueFactory<Prescription, String>("name"));
            colPrescDate.setCellValueFactory(new PropertyValueFactory<Prescription, LocalDateTime>("date"));
            colPrescDuration.setCellValueFactory(new PropertyValueFactory<Prescription, LocalDateTime>("duration"));
            colPrescDesc.setCellValueFactory(new PropertyValueFactory<Prescription, String>("desc"));
            colPatId.setCellValueFactory(new PropertyValueFactory<Prescription, Integer>("patientId"));
            colPatName.setCellValueFactory(new PropertyValueFactory<Prescription, String>("patientFName"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void addPrescription(){
        LocalDateTime prescDate = LocalDateTime.now();
        String prescDuration = inputPrescDuration.getText();
        String prescDesc = generatePresc(inputMedUsageList, selectMedNameList);
        int patientId = getPatientId(selectPatientFName.getSelectionModel().getSelectedItem());
        PrescriptionModelController pc = new PrescriptionModelController();
        Prescription prescription = new Prescription(prescDate, prescDuration, prescDesc, patientId);

        try {
            pc.insert(prescription);
            System.out.println("Prescription added");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clearAllField();
        loadTableContent();

    }

    private void deletePrescription(int id){
        PrescriptionModelController pc = new PrescriptionModelController();
        try {
            pc.delete(id);
            System.out.println("presc deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearAllField();
        loadTableContent();
    }

    private void usePrecription(){

        for (PrescMedInfo prescMedInfo : currMedUsageList){
            try{
                int newQuantity = 0;
                int medQuantity = (prescMedInfo.getAfternoonQuantity() + prescMedInfo.getMorningQuantity() + prescMedInfo.getNoonQuantity()) * prescMedInfo.getDuration();
                int medId = getMedId(prescMedInfo.getMedName());

                MedicamentModelController mc = new MedicamentModelController();
                ResultSet rs = mc.selectBy("medId", String.valueOf(medId));

                if (rs.next()){
                    int currentQuantity = rs.getInt("medQuantity");

                    newQuantity = currentQuantity + medQuantity;
                    mc.updateBy("medQuantity", newQuantity, "medId", medId);
                    System.out.println("Medicament updated");
                }
                loadTableContent();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void loadTableContent() {
        ObservableList<Prescription> prescriptions = FXCollections.observableArrayList();
        PrescriptionModelController pc = new PrescriptionModelController();

        try{
            ResultSet rs = pc.selectJoin();
            while (rs.next()){
                LocalDateTime prescDate = rs.getDate("prescDate").toLocalDate().atTime(LocalTime.now());
                String prescDuration = rs.getString("prescDuration");
                String prescDesc = rs.getString("prescDesc");
                String patientFName = rs.getString("patientFName");
                int prescId = rs.getInt("prescId");
                int patientId = rs.getInt("patientId");
                prescriptions.add(new Prescription(prescId, prescDate, prescDuration, prescDesc, patientId, patientFName));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        tablePrescription.setItems(prescriptions);
    }


    private String generatePresc(List<MedUsage> medUsageList, List<ChoiceBox<String>> medNameList){
        StringBuilder prescDesc = new StringBuilder();
        for (int i = 0; i < medNameList.size(); i++){
            String medName = medNameList.get(i).getSelectionModel().getSelectedItem();
            String morningQuantity = medUsageList.get(i).getMorningQuantity();
            String noonQuantity = medUsageList.get(i).getNoonQuantity();
            String afternoonQuantity = medUsageList.get(i).getAfternoonQuantity();

            prescDesc.append(medName).append("=>").append(morningQuantity).append(":").append(afternoonQuantity).append(":").append(noonQuantity).append(";");
        }
        return  String.valueOf(prescDesc);
    }

    private void generateTextField(int fieldCount) {
        inputPrescMedNum.setText(String.valueOf(fieldCount));
        medicamentContainer.setSpacing(16);
        medicamentContainer.setPrefWidth(460);
        medicamentContainer.getChildren().clear();

        for (int i = 0; i < fieldCount; i++) {
            HBox hBox = new HBox(8);
            ChoiceBox<String> selectMedName = new ChoiceBox<>();
            TextField inputMedQuantity = new TextField();
            TextField morningQuantity = new TextField();
            TextField noonQuantity = new TextField();
            TextField afternoonQuantity = new TextField();
            inputMedQuantity.setPromptText("Matin");

            selectMedName.setPrefWidth(240);
            selectMedName.getStyleClass().add("input-md");
            selectMedName.setPrefHeight(36);

            setMedUsageInputStyle(morningQuantity, "Matin");
            setMedUsageInputStyle(afternoonQuantity, "Midi");
            setMedUsageInputStyle(noonQuantity, "Soir");


            hBox.getChildren().addAll(selectMedName, morningQuantity, noonQuantity, afternoonQuantity);
            medicamentContainer.getChildren().add(hBox);

            selectMedNameList.add(selectMedName);
            inputMedUsageList.add(new MedUsage(morningQuantity, noonQuantity, afternoonQuantity));
            setSelectMedName(selectMedName);
        }

    }


    private void setMedNameAndMedUsageList(String prescDesc){
        currMedUsageList.clear();
        String[] prescriptions = prescDesc.split(";");
        int inputNumber = prescriptions.length;

//        generateTextField(inputNumber);


        for (int i = 0; i < inputNumber; i++){
            String item = prescriptions[i];
            String[] parts = item.split("=>");
            String medName = parts[0];
            String[] quantities = parts[1].split(":");

            int morningQuantity = Integer.parseInt(quantities[0]);
            int noonQuantity = Integer.parseInt(quantities[1]);
            int afternoonQuantity = Integer.parseInt(quantities[2]);
            int prescDuration = Integer.parseInt(currSelectedPrescRow.get(0).getDuration());

            currMedUsageList.add(new PrescMedInfo(morningQuantity, noonQuantity, afternoonQuantity, medName, prescDuration));

        }
    }

    private void handlePrescRowSelected(Prescription newSelection) {
        updateCurrSelectedPrescRow(newSelection);
        setFieldsValue(newSelection);
        updateButtonState();
    }

    private void updateCurrSelectedPrescRow(Prescription newSelection) {
        int prescId = newSelection.getId();
        LocalDateTime prescDate = LocalDateTime.now();
        String prescDuration = inputPrescDuration.getText();
        String prescDesc = generatePresc(inputMedUsageList, selectMedNameList);
        int patientId = getPatientId(selectPatientFName.getSelectionModel().getSelectedItem());

        currSelectedPrescRow.clear();
        currSelectedPrescRow.add(new Prescription(prescId,prescDate, prescDuration, prescDesc, patientId));
    }

    private void setFieldsValue(Prescription newSelection) {
        inputPrescDuration.setText(newSelection.getDuration());
        selectPatientFName.setValue(newSelection.getPatientFName());
        inputPrescDesc.setText(newSelection.getDesc());
        setMedNameAndMedUsageList(newSelection.getDesc());
        updateButtonState();
    }

    private void setSelectMedName(ChoiceBox<String> selectMedName){
       for (String medName : medNameInDb){
           selectMedName.getItems().add(medName);
       }
       selectMedName.getSelectionModel().select(0);
    }

    private ResultSet getMedName(){
        try {
            MedicamentModelController mc = new MedicamentModelController();
            return  mc.selectAll();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private int getPatientId(String patientName){
        PatientModelController pc = new PatientModelController();
        try {
            ResultSet rs = pc.selectBy("patientFName", patientName);
            if (rs.next())
                return rs.getInt("patientId");
            return -1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void updateViews(){
        setMedNameInDb(getMedName());
        initSelectPatientName();
    }

    private void initSelectPatientName(){
        try{
            PatientModelController pc = new PatientModelController();
            ResultSet rs = pc.selectAll();
            while(rs.next()){
                selectPatientFName.getItems().add(rs.getString("patientFName"));
            }
        }catch( Exception ex){
            ex.printStackTrace();
        }
        selectPatientFName.getSelectionModel().select(0);
    }

    private void setMedNameInDb(ResultSet medName){
        try{
            ResultSet rs = medName;
            int i = 0;
            while (rs.next()){
                medNameInDb.add(rs.getString("medName"));
                i++;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private int getMedId(String medName){
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

    private void updateButtonState() {
        System.out.println("updayed");
        boolean validMedInputs = true;
        for ( MedUsage medUsage: inputMedUsageList){
            boolean validMedInput = validText(medUsage.inputAfternoonQuantity(), new ValidNumber()) && validText(medUsage.inputNoonQuantity(), new ValidNumber()) && validText(medUsage.inputMorningQuantity(), new ValidNumber());
            if (!validMedInput) {
                validMedInputs = false;
                break;
            }
        }

        boolean allFieldValid = validMedInputs  && validText(inputPrescMedNum, new ValidNumber()) && validText(inputPrescDuration, new ValidNumber());


        boolean canDelete =  false;
        boolean canUse = false;
        boolean canEdit = false;

        if (!currSelectedPrescRow.isEmpty())
            canDelete =currSelectedPrescRow.get(0).getId() > 0;

        btnAdd.setDisable(false);
        btnDelete.setDisable(!canDelete);
        btnUse.setDisable(!canDelete);
        btnEdit.setDisable(!canEdit);
    }

    private void clearAllField() {
        inputPrescMedNum.setText("1");
        inputMedUsageList.get(0).clear();
        inputPrescDesc.clear();
        selectPatientFName.getSelectionModel().select(0);
        inputPrescDuration.clear();
    }

    public boolean validText(TextField textField, FieldValidator fieldValidator) {
        return ValidationUtil.validTextField(textField, fieldValidator);
    }

    private void setMedUsageInputStyle(TextField textField, String placeholder){
        textField.setPrefWidth(70);
        textField.setPrefWidth(210);
        textField.setPromptText(placeholder);
        textField.setStyle("-fx-border-color : #1f1f1f");
        textField.getStyleClass().add("input");
    }
}
