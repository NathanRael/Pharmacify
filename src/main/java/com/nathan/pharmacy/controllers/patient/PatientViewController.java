package com.nathan.pharmacy.controllers.patient;

import com.nathan.pharmacy.controllers.form.ValidEmail;
import com.nathan.pharmacy.controllers.form.ValidName;
import com.nathan.pharmacy.controllers.form.ValidPhone;
import com.nathan.pharmacy.controllers.form.ValidText;
import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.interfaces.FieldValidator;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.Patient;
import com.nathan.pharmacy.utils.HistoryUtil;
import com.nathan.pharmacy.utils.Session;
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

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class PatientViewController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnInvoice;

    @FXML
    private TableColumn<Patient, String> colAddress;

    @FXML
    private TableColumn<Patient, String> colEmail;

    @FXML
    private TableColumn<Patient, String> colFName;

    @FXML
    private TableColumn<Patient, Integer> colId;

    @FXML
    private TableColumn<Patient, String> colLName;

    @FXML
    private TableColumn<Patient, String> colPhone;

    @FXML
    private TextField inputPatAddress;

    @FXML
    private TextField inputPatEmail;

    @FXML
    private TextField inputPatFName;

    @FXML
    private TextField inputPatLName;

    @FXML
    private TextField inputPatPhone;

    @FXML
    private ChoiceBox<String> selectPatientFilter;

    @FXML
    private TextField inputPatientSearch;

    @FXML
    private TableView<Patient> tablePatient;

    private final List<Patient> currSelectedPatientRow = new ArrayList<>();

    @FXML
    void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) clearAllField();
        updateButtonState();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setOnAction(event -> addPatient());
        btnEdit.setOnAction(event -> editPatient());
        btnDelete.setOnAction(event -> deletePatient(currSelectedPatientRow.get(0).getId()));
        btnInvoice.setVisible(false);
        btnInvoice.setOnAction(event -> generateInvoice(currSelectedPatientRow.get(0).getId()));

        selectPatientFilter.getItems().addAll("Id", "Nom", "Adresse");
        selectPatientFilter.getSelectionModel().select(0);


        initTableView();
        listenToEvent();
        try {
            loadTableContent();
            tablePatient.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    handlePatientRowSelected(newSelection);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTableView() {
        try {
            colId.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
            colPhone.setCellValueFactory(new PropertyValueFactory<Patient, String>("phone"));
            colAddress.setCellValueFactory(new PropertyValueFactory<Patient, String>("address"));
            colEmail.setCellValueFactory(new PropertyValueFactory<Patient, String>("email"));
            colFName.setCellValueFactory(new PropertyValueFactory<Patient, String>("firstName"));
            colLName.setCellValueFactory(new PropertyValueFactory<Patient, String>("lastName"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void listenToEvent() {
        EventHandler<Event> keyTypeHandler = event -> updateButtonState();

        inputPatFName.setOnKeyTyped(keyTypeHandler);
        inputPatLName.setOnKeyTyped(keyTypeHandler);
        inputPatPhone.setOnKeyTyped(keyTypeHandler);
        inputPatEmail.setOnKeyTyped(keyTypeHandler);
        inputPatAddress.setOnContextMenuRequested(keyTypeHandler);

        inputPatientSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                try {
                    loadTableContent();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                searchPatient();
            }
        });
    }

    private void searchPatient() {
        String search = inputPatientSearch.getText();
        String filterMode = selectPatientFilter.getSelectionModel().getSelectedItem();

        try {
            ObservableList<Patient> patients = FXCollections.observableArrayList();
            PatientModelController pc = new PatientModelController();
            ResultSet rs = null;
            switch (filterMode) {
                case "Id" -> rs = pc.searchLike("patientId", search);
                case "Nom" -> rs = pc.searchLike("patientFName", search);
                case "Adresse" -> rs = pc.searchLike("patientAddress", search);
                default -> rs = pc.searchLike("patientId", search);
            }

            while(rs.next()){
                int patientId = rs.getInt("patientId");
                String patientFName = rs.getString("patientFName");
                String patientLName = rs.getString("patientLName");
                String patientPhone = rs.getString("patientPhone");
                String patientAddress = rs.getString("patientAddress");
                String patientEmail = rs.getString("patientEmail");
                patients.add(new Patient(patientId, patientFName, patientLName, patientPhone, patientAddress, patientEmail));
            }
            tablePatient.setItems(patients);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void addPatient() {
        int patientId = currSelectedPatientRow.get(0).getId();
        String patientFName = inputPatFName.getText();
        String patientLName = inputPatLName.getText();
        String patientPhone = inputPatPhone.getText();
        String patientAddress = inputPatAddress.getText();
        String patientEmail = inputPatEmail.getText();

        try {
            PatientModelController pc = new PatientModelController();
            Patient patient = new Patient(patientId, patientFName, patientLName, patientPhone, patientAddress, patientEmail);

            pc.insert(patient);
            HistoryUtil.pushHistory(Session.getInstance().getUserName(), String.format("Ajout d'un patient ( %s ) ", patientFName));
            System.out.println("Patient added");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        clearAllField();
        loadTableContent();
    }

    private void deletePatient(int id) {
        try {
            PatientModelController pc = new PatientModelController();
            ResultSet rs = pc.selectBy("patientId", String.valueOf(id));
            rs.next();
            pc.delete(id);
            HistoryUtil.pushHistory(Session.getInstance().getUserName(), String.format("Suppression d'un patient ( %s ) ", rs.getString("patientFName")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        clearAllField();
        loadTableContent();
    }

    private void editPatient() {
        int patientId = currSelectedPatientRow.get(0).getId();
        String patientFName = currSelectedPatientRow.get(0).getFirstName();
        String patientLName = currSelectedPatientRow.get(0).getLastName();
        String patientPhone = currSelectedPatientRow.get(0).getPhone();
        String patientAddress = currSelectedPatientRow.get(0).getAddress();
        String patientEmail = currSelectedPatientRow.get(0).getEmail();

        try {
            PatientModelController pc = new PatientModelController();
            Patient patient = new Patient(patientId, patientFName, patientLName, patientPhone, patientAddress, patientEmail);

            pc.update(patient);
            System.out.println("patient updated");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        loadTableContent();
        clearAllField();
    }

    private void generateInvoice(int patientId) {
        loadTableContent();
    }


    private void loadTableContent() {
        ObservableList<Patient> patient = FXCollections.observableArrayList();
        PatientModelController pc = new PatientModelController();

        ResultSet rs = null;
        try {
            rs = pc.selectAll();

            while (rs.next()) {
                int patientId = rs.getInt("patientId");
                String patientFName = rs.getString("patientFName");
                String patientLName = rs.getString("patientLName");
                String patientPhone = rs.getString("patientPhone");
                String patientAddress = rs.getString("patientAddress");
                String patientEmail = rs.getString("patientEmail");
                patient.add(new Patient(patientId, patientFName, patientLName, patientPhone, patientAddress, patientEmail));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        tablePatient.setItems(patient);
    }

    public void updateCurrSelectedPatRow(Patient selectedRow) {
        int patientId = selectedRow.getId();
        String patientFName = selectedRow.getFirstName();
        String patientLName = selectedRow.getLastName();
        String patientPhone = selectedRow.getPhone();
        String patientAddress = selectedRow.getAddress();
        String patientEmail = selectedRow.getEmail();
        currSelectedPatientRow.clear();
        currSelectedPatientRow.add(new Patient(patientId, patientFName, patientLName, patientPhone, patientAddress, patientEmail));
    }


    private void handlePatientRowSelected(Patient newSelection) {
        updateCurrSelectedPatRow(newSelection);
        setFieldsValue(newSelection);
        updateButtonState();
    }

    private void setFieldsValue(Patient newSelection) {
        inputPatPhone.setText(newSelection.getPhone());
        inputPatAddress.setText(newSelection.getAddress());
        inputPatEmail.setText(newSelection.getEmail());
        inputPatLName.setText(newSelection.getLastName());
        inputPatFName.setText(newSelection.getFirstName());
    }

    private void updateButtonState() {
        boolean allFieldCorrect = validText(inputPatFName, new ValidName()) && validText(inputPatLName, new ValidName()) && validText(inputPatPhone, new ValidPhone()) && validText(inputPatAddress, new ValidName()) && validText(inputPatEmail, new ValidEmail());
        boolean canDelete = currSelectedPatientRow.get(0).getId() > 0;
        boolean canEdit = allFieldCorrect && !currSelectedPatientRow.isEmpty();

        btnAdd.setDisable(!allFieldCorrect);
        btnDelete.setDisable(!canDelete);
        btnEdit.setDisable(!canEdit);
    }

    private void clearAllField() {
        inputPatAddress.clear();
        inputPatEmail.clear();
        inputPatFName.clear();
        inputPatLName.clear();
        inputPatPhone.clear();
        updateButtonState();
    }

    private boolean validText(TextField textField, FieldValidator fieldValidator) {
        return ValidationUtil.validTextField(textField, fieldValidator);
    }
}
