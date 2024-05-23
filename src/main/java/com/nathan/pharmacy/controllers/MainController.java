package com.nathan.pharmacy.controllers;

import com.nathan.pharmacy.models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane mainParent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            SceneChanger.updateSubScene(mainParent, newVal);
        } );
    }
}
