package com.nathan.pharmacy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private SceneChanger sceneChanger;
    @FXML
    private FlowPane btnDashboard;

    @FXML
    private Button btnHistory;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnNavDashboard;

    @FXML
    private Button btnNavMedicine;

    @FXML
    private Button btnNavPatient;

    @FXML
    private Button btnNavPurchase;

    @FXML
    private Button btnNavSupplier;

    @FXML
    private Button btnNavUser;

    @FXML
    private AnchorPane dashboardAnchorPane;

    @FXML
    private VBox navbar;

    @FXML
    private VBox roleBadge;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void switchSceneTo(String sceneName) {
        Stage currentStage = (Stage)navbar.getScene().getWindow();
        SceneChanger.changeSceneTo(sceneName, currentStage);
    }

}
