package com.nathan.pharmacy.controllers;

import com.nathan.pharmacy.models.Model;

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

}
