package com.nathan.pharmacy.controllers.medicament;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicamentController implements Initializable {

    @FXML
    private Button btnAddMed;

    @FXML
    private Button btnEditMed;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField inputMedDesc;

    @FXML
    private TextField inputMedName;

    @FXML
    private TextField inputMedPrice;

    @FXML
    private TextField inputSearch;

    @FXML
    private ComboBox<String> selectFilterMedicament;

    @FXML
    private ListView<Object> tableMedList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
