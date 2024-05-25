package com.nathan.pharmacy.controllers.purchase;

import com.nathan.pharmacy.models.Purchase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class PurchaseViewController implements Initializable {

    @FXML
    private TableColumn<Purchase, String> colDesc;

    @FXML
    private TableColumn<Purchase, Date> colExpireDate;

    @FXML
    private TableColumn<Purchase, String> colName;

    @FXML
    private TableColumn<Purchase, Float> colPrice;

    @FXML
    private TableColumn<Purchase, Integer> colQuantity;

    @FXML
    private TableColumn<Purchase, String> colStockName;
    private TableColumn<Purchase, Integer> colMedId;

    @FXML
    private TextField inputMedName;

    @FXML
    private TextField inputMedName1;

    @FXML
    private TextField inputMedQuantity;

    @FXML
    private TextField inputPatientId;

    @FXML
    private TextField inputPatientName;

    @FXML
    private ChoiceBox<String> selectMedFilter;

    @FXML
    private TableView<Purchase> tableMedicament;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectMedFilter.getItems().addAll("Prix", "Expiration", "Test");
    }


}
