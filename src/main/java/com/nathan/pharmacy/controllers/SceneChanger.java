package com.nathan.pharmacy.controllers;

import com.nathan.pharmacy.contstants.Scenes;
import com.nathan.pharmacy.models.Model;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SceneChanger {

    public static void changeSceneTo(String sceneName, Stage currentStage){
        Model.getInstance().getViewFactory().closeStage(currentStage);

        switch (sceneName){
            case "login" -> Model.getInstance().getViewFactory().showLoginWindow();
            case "signup" -> Model.getInstance().getViewFactory().showSignupWindow();
            case "main" -> Model.getInstance().getViewFactory().showMainWindow();
            default ->  Model.getInstance().getViewFactory().showNotFoundWindow();
        }
    }

    public static void updateSubScene(BorderPane mainParent, String newVal){

        switch (newVal){
            case "purchase" -> mainParent.setCenter(Model.getInstance().getViewFactory().getPurchaseView());
            default -> mainParent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
        }
    }

}
