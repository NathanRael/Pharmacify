package com.nathan.pharmacy.views;

import com.nathan.pharmacy.controllers.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private final StringProperty selectedMenuItem;
    private ScrollPane dashboardView;
    private ScrollPane patientView;
    private ScrollPane prescriptionView;
    private ScrollPane purchaseView;
    private AnchorPane userView;
    private AnchorPane historyView;
    private AnchorPane medicamentView;
    private AnchorPane supplierView;
    private AnchorPane deliveryView;


    public ViewFactory() {
        this.selectedMenuItem = new SimpleStringProperty("");
    }

    ;

    public ScrollPane getDashboardView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
            dashboardView = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return dashboardView;
    }

    public AnchorPane getHistoryView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history-view.fxml"));
            historyView = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return historyView;
    }

    public AnchorPane getSupplierView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("supplier-view.fxml"));
            supplierView = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return supplierView;
    }

    public ScrollPane getPatientView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("patient-view.fxml"));
            patientView = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return patientView;
    }

    public ScrollPane getPrescriptionView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("prescription-view.fxml"));
            prescriptionView = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prescriptionView;
    }

    public AnchorPane getDeliveryView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("delivery-view.fxml"));
            deliveryView = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return deliveryView;
    }

    public ScrollPane getPurchaseView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("purchase-view.fxml"));
            purchaseView = fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return purchaseView;
    }

    public AnchorPane getMedicamentView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("medicament-view.fxml"));
            medicamentView = fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return medicamentView;
    }

    public AnchorPane getUserView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user-view.fxml"));
            userView = fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return userView;
    }

    public void showLoginWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        createStage(fxmlLoader);
    }

    public void showSignupWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup-view.fxml"));
        createStage(fxmlLoader);
    }

    public void showMainWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        MainController mainController = new MainController();
        fxmlLoader.setController(mainController);
        createStage(fxmlLoader);
    }

    public void showNotFoundWindow() {

        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);
        Stage stage = new Stage();

        Text txtMessage = new Text();

        txtMessage.setText("La fenêtre est introuvable !");
        txtMessage.setFill(Color.RED);
        txtMessage.setX(50);
        txtMessage.setY(50);
//        txtMessage.setFont();


        root.getChildren().add(txtMessage);

        stage.setTitle("Window not found");
        System.out.println("Window not found");
        stage.setScene(scene);
        stage.show();
    }

    private void createStage(FXMLLoader fxmlLoader) {
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());

        } catch (IOException ex) {
            System.out.println(ex);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Pharmacify");
        if (false) {
            stage.setFullScreen(true);
        }
//        stage.setResizable(false);
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }


}
