package com.nathan.pharmacy.controllers.purchase;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseController implements Initializable {

    @FXML
    private ChoiceBox<String> selectMedFilter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectMedFilter.getItems().addAll("Prix", "Expiration", "Test");
    }
}
