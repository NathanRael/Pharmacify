package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.controllers.purchase.PurchaseViewController;
import com.nathan.pharmacy.contstants.ScenesName;
import com.nathan.pharmacy.contstants.SubScenesName;
import com.nathan.pharmacy.models.Singleton;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SceneChanger {
    private PurchaseViewController purchaseViewController;

    public SceneChanger(){};

    public static void changeSceneTo(ScenesName scenesName, Stage currentStage){
        Singleton.getInstance().getViewFactory().closeStage(currentStage);

        switch (scenesName){
            case LOGIN -> Singleton.getInstance().getViewFactory().showLoginWindow();
            case SIGNUP -> Singleton.getInstance().getViewFactory().showSignupWindow();
            case MAIN-> Singleton.getInstance().getViewFactory().showMainWindow();
            default ->  Singleton.getInstance().getViewFactory().showNotFoundWindow();
        }
    }

    public  void updateSubScene(BorderPane mainParent, SubScenesName newVal){
        switch (newVal){
            case DASHBOARD ->{
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getDashboardView());
            }
            case PURCHASE -> {
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getPurchaseView());
            }
            case MEDICAMENT -> {
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getMedicamentView());
            }
            case SUPPLIER -> {
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getSupplierView());
            }
            case DELIVERY -> {
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getDeliveryView());
            }
            case PATIENT -> {
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getPatientView());
            }
            case PRESCRIPTION -> {
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getPrescriptionView());
            }
            case USER -> {
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getUserView());
            }
            case HISTORY -> {
                mainParent.setCenter(Singleton.getInstance().getViewFactory().getHistoryView());
            }
            default -> {
                Singleton.getInstance().getViewFactory().showNotFoundWindow();
            }
        }
    }

}
