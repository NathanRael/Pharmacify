package com.nathan.pharmacy.controllers.supplier;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class SupplierViewController {
    @FXML
    private Button btnPurchase;

    @FXML
    private Button btnPurchase1;

    @FXML
    private Button btnPurchase2;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colSupName;

    @FXML
    private TableColumn<?, ?> colSupPhone;

    @FXML
    private TextField inputMedQuantity;

    @FXML
    private TextField inputPatientId;

    @FXML
    private TextField inputTotalPrice;

    @FXML
    private TableView<?> tableSupplier;

    @FXML
    void handleInputPatientKeyTyped(KeyEvent event) {

    }

    @FXML
    void handleKeyPressed(KeyEvent event) {

    }

    @FXML
    void handleQuantityKeyTyped(KeyEvent event) {

    }
}
