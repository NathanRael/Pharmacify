package com.nathan.pharmacy.views;

import com.nathan.pharmacy.Main;
import com.nathan.pharmacy.controllers.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private final StringProperty selectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane purchaseView;


    public ViewFactory(){
        this.selectedMenuItem = new SimpleStringProperty("");
    };

    public AnchorPane getDashboardView(){
        if (dashboardView == null){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
                dashboardView = fxmlLoader.load();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getPurchaseView(){
        if (purchaseView == null){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("purchase-view.fxml"));
                purchaseView = fxmlLoader.load();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return purchaseView;
    }

    public void showLoginWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        createStage(fxmlLoader);
    }
    public void showSignupWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup-view.fxml"));
        createStage(fxmlLoader);
    }

    public void showMainWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        MainController mainController = new MainController();
        fxmlLoader.setController(mainController);
        createStage(fxmlLoader);
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

    private void createStage(FXMLLoader fxmlLoader){
        Scene scene = null;
        try{
            scene  = new Scene(fxmlLoader.load());

        }catch (IOException ex){
            ex.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Pharmacify");
        stage.setResizable(false);
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }

    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }

}
