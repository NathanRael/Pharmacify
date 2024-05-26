package com.nathan.pharmacy.controllers;

import com.nathan.pharmacy.controllers.purchase.PurchaseViewController;
import com.nathan.pharmacy.models.Singleton;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SceneChanger {
    private PurchaseViewController purchaseViewController;

    public SceneChanger(){};

    public static void changeSceneTo(String sceneName, Stage currentStage){
        Singleton.getInstance().getViewFactory().closeStage(currentStage);

        switch (sceneName){
            case "login" -> Singleton.getInstance().getViewFactory().showLoginWindow();
            case "signup" -> Singleton.getInstance().getViewFactory().showSignupWindow();
            case "main" -> Singleton.getInstance().getViewFactory().showMainWindow();
            default ->  Singleton.getInstance().getViewFactory().showNotFoundWindow();
        }
    }

    public  void updateSubScene(BorderPane mainParent, String newVal){
        switch (newVal){
            case "dashboard" ->{
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getDashboardView());
            }
            case "purchase" -> {
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getPurchaseView());
            }
            case "medicament" -> {
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getMedicamentView());
            }
            default -> {
                Singleton.getInstance().getViewFactory().showNotFoundWindow();
            }
        }
    }

}
