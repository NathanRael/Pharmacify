package com.nathan.pharmacy.controllers;

import com.nathan.pharmacy.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneChanger {

    public static void switchScene(AnchorPane currentAnchorPane, String fxmlFile) throws IOException {
            AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("views/" + fxmlFile)));
            currentAnchorPane.getChildren().removeAll();
            currentAnchorPane.getChildren().setAll(nextAnchorPane);
    }

}
