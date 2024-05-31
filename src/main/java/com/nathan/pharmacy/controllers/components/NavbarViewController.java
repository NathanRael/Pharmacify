package com.nathan.pharmacy.controllers.components;

import com.nathan.pharmacy.utils.SceneChanger;
import com.nathan.pharmacy.utils.Session;
import com.nathan.pharmacy.contstants.ScenesName;
import com.nathan.pharmacy.contstants.SubScenesName;
import com.nathan.pharmacy.models.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NavbarViewController implements Initializable {


    @FXML
    private Button btnLogout;

    @FXML
    private Button btnNavDashboard;

    @FXML
    private Button btnNavDelivery;

    @FXML
    private Button btnNavHistory;

    @FXML
    private Button btnNavMedicine;

    @FXML
    private Button btnNavPatient;

    @FXML
    private Button btnNavPrescription;

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
    private Text txtUserName;

    @FXML
    private Text txtUserRole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleUserSession();
    }

    private void handleUserSession() {
        if (Session.getInstance().sessionExist()) {
            String userRole = Integer.parseInt(Session.getInstance().getUserRole()) == 1 ? "Utilisateur" : "SuperUtilisateur";
            txtUserName.setText(Session.getInstance().getUserName());
            txtUserRole.setText(userRole);

            if (Integer.parseInt(Session.getInstance().getUserRole()) == 1) {
                hideAdminPart();
            }
        } else {
//            logout();
        }
    }

    private void hideAdminPart() {
        btnNavHistory.setVisible(false);
        btnNavSupplier.setVisible(false);
        btnNavUser.setVisible(false);

        btnNavHistory.setDisable(true);
        btnNavSupplier.setDisable(true);
        btnNavUser.setDisable(true);
    }

    @FXML
    void handleLogout(ActionEvent event) {
        logout();
    }

    private void logout() {
        Session.getInstance().clear();
        switchSceneTo(ScenesName.LOGIN);
    }

    @FXML
    void switchToDashboard(ActionEvent event) {
        switchSubsceneTo(SubScenesName.DASHBOARD);
    }

    @FXML
    void switchToPurchase(ActionEvent event) {
        switchSubsceneTo(SubScenesName.PURCHASE);
    }

    @FXML
    void switchToMedicine(ActionEvent event) {
        switchSubsceneTo(SubScenesName.MEDICAMENT);
    }

    @FXML
    void switchToSupplier(ActionEvent event) {
        switchSubsceneTo(SubScenesName.SUPPLIER);
    }

    @FXML
    void switchToDelivery(ActionEvent event) {
        switchSubsceneTo(SubScenesName.DELIVERY);
    }

    @FXML
    void switchToPatient(ActionEvent event) {
        switchSubsceneTo(SubScenesName.PATIENT);
    }

    @FXML
    void switchToPrescription(ActionEvent event) {
        switchSubsceneTo(SubScenesName.PRESCRIPTION);
    }

    @FXML
    void switchToUser(ActionEvent event) {
        switchSubsceneTo(SubScenesName.USER);
    }

    @FXML
    void switchToHistory(ActionEvent event) {
        switchSubsceneTo(SubScenesName.HISTORY);
    }


    public void switchSubsceneTo(SubScenesName subScenesName) {
        Singleton.getInstance().getViewFactory().getSelectedMenuItem().set(String.valueOf(subScenesName));
    }

    public void switchSceneTo(ScenesName sceneName) {
        Stage currentStage = (Stage) navbar.getScene().getWindow();
        SceneChanger.changeSceneTo(sceneName, currentStage);
    }


}
