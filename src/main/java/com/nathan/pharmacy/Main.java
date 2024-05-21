package com.nathan.pharmacy;


import com.nathan.pharmacy.controllers.SceneChanger;
import com.nathan.pharmacy.models.Model;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) {
        SceneChanger.changeSceneTo("main", stage);
    }
    public static void main(String[] args) {
        launch();
    }
}
