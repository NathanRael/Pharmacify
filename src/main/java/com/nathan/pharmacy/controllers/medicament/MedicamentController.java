package com.nathan.pharmacy.controllers.medicament;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private ChoiceBox<String> selectMedFilter;

    @FXML
    private ListView<Object> tableMedList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectMedFilter.getItems().addAll("Prix", "Date");
    }
}
