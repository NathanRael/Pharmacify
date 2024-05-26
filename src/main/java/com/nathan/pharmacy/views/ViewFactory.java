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
    private AnchorPane purchaseView;
    private AnchorPane medicamentView;
    private AnchorPane supplierView;


    public ViewFactory(){
        this.selectedMenuItem = new SimpleStringProperty("");
    };

    public ScrollPane getDashboardView(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
            dashboardView = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return dashboardView;
    }

    public AnchorPane getSupplierView(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("supplier-view.fxml"));
            supplierView = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return supplierView;
    }

    public AnchorPane getPurchaseView(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("purchase-view.fxml"));
            purchaseView = fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return purchaseView;
    }
    public AnchorPane getMedicamentView(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("medicament-view.fxml"));
            medicamentView = fxmlLoader.load();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return medicamentView;
    }

    public void showLoginWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        createStage(fxmlLoader, false);
    }
    public void showSignupWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup-view.fxml"));
        createStage(fxmlLoader, false);
    }

    public void showMainWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        MainController mainController = new MainController();
        fxmlLoader.setController(mainController);
        createStage(fxmlLoader, false);
    }

    public void showNotFoundWindow(){

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

    private void createStage(FXMLLoader fxmlLoader, boolean fullscreen){
        Scene scene = null;
        try{
            scene  = new Scene(fxmlLoader.load());

        }catch (IOException ex){
            System.out.println(ex);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Pharmacify");
        if (fullscreen){
            stage.setFullScreen(true);
        }
//        stage.setResizable(false);
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }

    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }



}
