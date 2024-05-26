package com.nathan.pharmacy.controllers;

import com.nathan.pharmacy.controllers.medicament.MedicamentViewController;
import com.nathan.pharmacy.controllers.purchase.PurchaseViewController;
import com.nathan.pharmacy.models.Singleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane mainParent;

    private final SceneChanger sceneChanger = new SceneChanger();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Singleton.getInstance().getViewFactory().getSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            sceneChanger.updateSubScene(mainParent, newVal);
        } );
    }
}
