package com.nathan.pharmacy.controllers.components;

import com.nathan.pharmacy.controllers.SceneChanger;
import com.nathan.pharmacy.models.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavbarController {

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
    private VBox navbar;

    @FXML
    private VBox roleBadge;

    @FXML
    void logout(ActionEvent event) {
        switchSceneTo("login");
    }

    @FXML
    void switchToDashboard(ActionEvent event) {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("dashboard");
    }
    @FXML
    void switchToPurchase(ActionEvent event) {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("purchase");
    }

    @FXML
    void switchToHistory(ActionEvent event) {

    }

    @FXML
    void switchToMedicine(ActionEvent event) {

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

    public void switchSceneTo(String sceneName) {
        Stage currentStage = (Stage)navbar.getScene().getWindow();
        SceneChanger.changeSceneTo(sceneName, currentStage);
    }

}
