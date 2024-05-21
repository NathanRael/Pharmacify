package com.nathan.pharmacy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public class LoginController implements Initializable {

    private SceneChanger sceneChanger;
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignup;

    @FXML
    private TextField inputName;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private AnchorPane loginAnchorPane;


    @FXML
    private Label txtName;

    @FXML
    private Label txtPassword;


    @FXML
    void login(ActionEvent event) throws Exception {
        boolean currentUserFound = false;
        String name = inputName.getText();
        String password = inputPassword.getText();
        List<Object> currentUserInfoInDb = new ArrayList<>();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");

//        ObservableList<Object> users = FXCollections.observableArrayList();

        UserController uc = new UserController();
        ResultSet user;
        user = uc.selectBy("userName", name);

        while (user.next()){
            if (user.getString("userName").equalsIgnoreCase(name)){
                Collections.addAll(currentUserInfoInDb, user.getString("userId"), user.getString("userName"), user.getString("userPwd"));
                currentUserFound = true;
                break;
            }
        }

        if (currentUserFound){
            if (password.equals(currentUserInfoInDb.get(2))){
                //Switch scene
                alert.setContentText("Connexion ...");
                alert.showAndWait();
            }else{
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Mot de passe incorrect");
                alert.showAndWait();
            }
        }else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Utilisateur non trouvé");
            alert.showAndWait();
        }
    }

    @FXML
    void switchToSignup(ActionEvent event) throws IOException {
        switchSceneTo("signup-view.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void switchSceneTo(String sceneLocation) throws IOException {
        SceneChanger.switchScene(loginAnchorPane, sceneLocation);
    }
}
