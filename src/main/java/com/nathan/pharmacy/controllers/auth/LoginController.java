package com.nathan.pharmacy.controllers.auth;

import com.nathan.pharmacy.controllers.SceneChanger;
import com.nathan.pharmacy.controllers.user.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public class LoginController implements Initializable {

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

//    private final Stage currentStage = (Stage)btnLogin.getScene().getWindow();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void login(ActionEvent event) throws Exception {
        boolean currentUserFound = false;
        String name = inputName.getText();
        String password = inputPassword.getText();
        List<Object> currentUserInfoInDb = new ArrayList<>();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");


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
                alert.setContentText("Redirection ...");
                alert.showAndWait();
                switchSceneTo("main");
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
    void switchToSignup(ActionEvent event) {
       switchSceneTo("signup");
    }

    public void switchSceneTo(String sceneName) {
        Stage currentStage = (Stage)btnLogin.getScene().getWindow();
        SceneChanger.changeSceneTo(sceneName, currentStage);
    }

}
