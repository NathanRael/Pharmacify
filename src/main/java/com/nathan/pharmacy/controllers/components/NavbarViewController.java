package com.nathan.pharmacy.controllers.components;

import com.nathan.pharmacy.controllers.SceneChanger;
import com.nathan.pharmacy.models.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NavbarViewController implements Initializable {

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
    private Button btnNavPrescription;

    @FXML
    private VBox navbar;

    @FXML
    private VBox roleBadge;

    @FXML
    void logout(ActionEvent event) {
        switchSceneTo("login");
    }

    @FXML
    void switchToDashboard(ActionEvent event) {
        switchSubsceneTo("dashboard");
    }
    @FXML
    void switchToPurchase(ActionEvent event) {
        switchSubsceneTo("purchase");
    }

    @FXML
    void switchToMedicine(ActionEvent event) {
        switchSubsceneTo("medicament");
    }

    @FXML
    void switchToHistory(ActionEvent event) {

    }

    @FXML
    void switchToPrescription(ActionEvent event){

    }

    @FXML
    void switchToPatient(ActionEvent event) {

    }

    @FXML
    void switchToSupplier(ActionEvent event) {

    }

    @FXML
    void switchToUser(ActionEvent event) {

    }

    public void switchSubsceneTo(String sceneName){
        Singleton.getInstance().getViewFactory().getSelectedMenuItem().set(sceneName);
    }

    public void switchSceneTo(String sceneName) {
        Stage currentStage = (Stage)navbar.getScene().getWindow();
        SceneChanger.changeSceneTo(sceneName, currentStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
